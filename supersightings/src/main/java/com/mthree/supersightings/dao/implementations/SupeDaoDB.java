/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao.implementations;

import com.mthree.supersightings.dao.SupeDao;
import com.mthree.supersightings.dao.implementations.LocationDaoDB.LocationMapper;
import com.mthree.supersightings.dao.implementations.OrganizationDaoDB.OrganizationMapper;
import com.mthree.supersightings.entities.Location;
import com.mthree.supersightings.entities.Organization;
import com.mthree.supersightings.entities.Supe;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class SupeDaoDB implements SupeDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Supe getSupeById(int id) throws SuperSightingsPersistenceException {
        try {
            final String SELECT_SUPE_BY_ID = "SELECT * FROM supe WHERE id = ?";
            return jdbc.queryForObject(SELECT_SUPE_BY_ID, new SupeMapper(), id);
        } catch (DataAccessException ex) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    public List<Supe> getAllSupes() throws SuperSightingsPersistenceException {
        try {
            final String SELECT_ALL_SUPES = "SELECT * FROM supe";
            return jdbc.query(SELECT_ALL_SUPES, new SupeMapper());
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    @Transactional
    public Supe addSupe(Supe supe) throws SuperSightingsPersistenceException {
        try {
            final String INSERT_SUPE = "INSERT INTO supe(firstName, lastName, description, superPower) "
                    + "VALUES(?,?,?,?)";
            jdbc.update(INSERT_SUPE,
                    supe.getFirstName(),
                    supe.getLastName(),
                    supe.getDescription(),
                    supe.getSuperPower());
            
            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            supe.setId(newId);
            return supe;
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    public void updateSupe(Supe supe) throws SuperSightingsPersistenceException {
        try {
            final String UPDATE_SUPE = "UPDATE supe SET firstName = ?, lastName = ?, description = ?, superPower = ? "
                    + "WHERE id = ?";
            jdbc.update(UPDATE_SUPE,
                    supe.getFirstName(),
                    supe.getLastName(),
                    supe.getDescription(),
                    supe.getSuperPower(),
                    supe.getId());
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    @Transactional
    public void deleteSupeById(int id) throws SuperSightingsPersistenceException {
        try {
            final String DELETE_SUPEORGANIZATION = "DELETE FROM supeOrganization WHERE supeID = ?";
            jdbc.update(DELETE_SUPEORGANIZATION, id);
            
            final String DELETE_SUPESIGHTING = "DELETE FROM supeSighting WHERE supeID = ?";
            jdbc.update(DELETE_SUPESIGHTING, id);
            
            final String DELETE_SUPE = "DELETE FROM supe WHERE id = ?";
            jdbc.update(DELETE_SUPE, id);
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }
    
    @Override
    public List<Organization> getSupeOrganizations(int id) throws SuperSightingsPersistenceException {
        final String SELECT_ORGANIZATIONS_OF_SUPER = "SELECT o.* FROM organization o "
                + "JOIN supeOrganization cs ON cs.organizationId = o.id WHERE cs.supeId = ?";
        try {
            List<Organization> orgs = jdbc.query(SELECT_ORGANIZATIONS_OF_SUPER, new OrganizationMapper(), id);
            return orgs;
        } catch (DataAccessException e) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }
    
    @Override
    public List<Location> getSupeLocations(int id) throws SuperSightingsPersistenceException {
        final String SELECT_LOCATIONS_OF_SUPE = "SELECT l.* FROM location l JOIN sighting s on l.id = s.locationID "
                + "JOIN supeSighting st ON st.sightingID = s.id WHERE st.supeID = ?";
        try {
            List<Location> supes = jdbc.query(SELECT_LOCATIONS_OF_SUPE, new LocationMapper(), id);
            return supes;
        } catch (DataAccessException e) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }
    
    public static final class SupeMapper implements RowMapper<Supe> {

        @Override
        public Supe mapRow(ResultSet rs, int index) throws SQLException {
            Supe supe = new Supe();
            supe.setId(rs.getInt("id"));
            supe.setFirstName(rs.getString("firstName"));
            supe.setLastName(rs.getString("lastName"));
            supe.setDescription(rs.getString("description"));
            supe.setSuperPower(rs.getString("superPower"));

            return supe;
        }
    }    
}
