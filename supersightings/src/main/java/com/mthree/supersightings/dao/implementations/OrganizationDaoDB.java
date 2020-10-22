/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao.implementations;

import com.mthree.supersightings.dao.OrganizationDao;
import com.mthree.supersightings.dao.implementations.SupeDaoDB.SupeMapper;
import com.mthree.supersightings.entities.Organization;
import com.mthree.supersightings.entities.Supe;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author utkua
 */
@Repository
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) throws SuperSightingsPersistenceException {
        final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id = ?";
        try {
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            organization.setSupes(getSupesForOrganization(id));
            return organization;
        } catch(DataAccessException e) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    public List<Organization> getAllOrganizations() throws SuperSightingsPersistenceException {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        try {
            List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
            associateSupes(organizations);
            return organizations;
        } catch (DataAccessException e) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }

    }

    private void associateSupes(List<Organization> organizations) throws SuperSightingsPersistenceException {
        for (Organization organization : organizations) {
            organization.setSupes(getSupesForOrganization(organization.getId()));
        }
    }
    
    private List<Supe> getSupesForOrganization(int id) throws SuperSightingsPersistenceException {
        final String SELECT_STUDENTS_FOR_ORGANIZATION = "SELECT s.* FROM supe s "
                + "JOIN supeOrganization cs ON cs.supeId = s.id WHERE cs.organizationId = ?";
        try {
            List<Supe> supes = jdbc.query(SELECT_STUDENTS_FOR_ORGANIZATION, new SupeMapper(), id);
            return supes;
        } catch (Exception e) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) throws SuperSightingsPersistenceException {
        try {
            final String INSERT_ORGANIZATION = "INSERT INTO organization(name, description, phoneNumber, email, address, postCode, city, country) "
                    + "VALUES(?,?,?,?,?,?,?,?)";
            jdbc.update(INSERT_ORGANIZATION,
                    organization.getName(), 
                    organization.getDescription(),
                    organization.getPhoneNumber(),
                    organization.getEmail(),
                    organization.getAddress(),
                    organization.getPostCode(),
                    organization.getCity(),
                    organization.getCountry());

            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            organization.setId(newId);
            insertOrganizationSupe(organization);
            return organization;
        } catch (Exception e) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    private void insertOrganizationSupe(Organization organization) throws SuperSightingsPersistenceException {
        final String INSERT_ORGANIZATION_STUDENT = "INSERT INTO "
                + "supeOrganization(organizationId, supeId) VALUES(?,?)";
        for(Supe supe : organization.getSupes()) {
            jdbc.update(INSERT_ORGANIZATION_STUDENT, 
                    organization.getId(),
                    supe.getId());
        }
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) throws SuperSightingsPersistenceException {
        try {
            final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, description = ?, phoneNumber = ?, email = ?, address = ?, postCode = ?, city = ?, country = ? "
                    + "WHERE id = ?";
            jdbc.update(UPDATE_ORGANIZATION, 
                    organization.getName(), 
                    organization.getDescription(),
                    organization.getPhoneNumber(),
                    organization.getEmail(),
                    organization.getAddress(),
                    organization.getPostCode(),
                    organization.getCity(),
                    organization.getCountry(),
                    organization.getId());

            final String DELETE_ORGANIZATION_STUDENT = "DELETE FROM supeOrganization WHERE organizationId = ?";
            jdbc.update(DELETE_ORGANIZATION_STUDENT, organization.getId());
            insertOrganizationSupe(organization);
        } catch (Exception e) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }

    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) throws SuperSightingsPersistenceException {
        try {
            final String DELETE_ORGANIZATION_STUDENT = "DELETE FROM supeOrganization WHERE organizationId = ?";
            jdbc.update(DELETE_ORGANIZATION_STUDENT, id);

            final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE id = ?";
            jdbc.update(DELETE_ORGANIZATION, id);
        } catch (Exception e) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    public List<Organization> getOrganizationsForSupe(int id) throws SuperSightingsPersistenceException {
        try {
            final String SELECT_ORGANIZATIONS_FOR_STUDENT = "SELECT c.* FROM organization c JOIN "
                    + "supeOrganization cs ON cs.organizationId = c.Id WHERE cs.supeId = ?";
            List<Organization> organizations = jdbc.query(SELECT_ORGANIZATIONS_FOR_STUDENT, 
                    new OrganizationMapper(), id);
            associateSupes(organizations);
            return organizations;
        } catch (Exception e) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }
   
    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setPhoneNumber(rs.getString("phoneNumber"));
            organization.setEmail(rs.getString("email"));
            organization.setAddress(rs.getString("address"));
            organization.setPostCode(rs.getString("postCode"));
            organization.setCity(rs.getString("city"));
            organization.setCountry(rs.getString("country"));
            return organization;
        }
    }
}
