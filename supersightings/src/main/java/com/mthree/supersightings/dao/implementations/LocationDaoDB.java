/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao.implementations;

import com.mthree.supersightings.dao.LocationDao;
import com.mthree.supersightings.dao.implementations.SupeDaoDB.SupeMapper;
import com.mthree.supersightings.entities.Location;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) throws SuperSightingsPersistenceException {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    public List<Location> getAllLocations() throws SuperSightingsPersistenceException {
        try {
            final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
            return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    @Transactional
    public Location addLocation(Location location) throws SuperSightingsPersistenceException {
        try {
            final String INSERT_LOCATION = "INSERT INTO location(name, description, latitude, longitude, address, postCode, city, country)"
                    + "VALUES(?,?,?,?,?,?,?,?)";
            jdbc.update(INSERT_LOCATION,
                    location.getName(),
                    location.getDescription(),
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getAddress(),
                    location.getPostCode(),
                    location.getCity(),
                    location.getCountry());
            
            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            location.setId(newId);
            return location;
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    public void updateLocation(Location location) throws SuperSightingsPersistenceException {
        try {
            final String UPDATE_LOCATION = "UPDATE location SET name = ?, description = ?, latitude = ?, longitude = ?, address = ?, postCode = ?, city = ?, country = ?"
                    + "WHERE id = ?";
            jdbc.update(UPDATE_LOCATION,
                    location.getName(),
                    location.getDescription(),
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getAddress(),
                    location.getPostCode(),
                    location.getCity(),
                    location.getCountry(),
                    location.getId());
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) throws SuperSightingsPersistenceException {
        try {
            final String DELETE_SIGHTING_SUPE = "DELETE FROM supeSighting WHERE sightingID IN (SELECT id FROM sighting WHERE locationId = ?)";
            jdbc.update(DELETE_SIGHTING_SUPE, id);
            
            final String DELETE_SIGHTING = "DELETE FROM sighting WHERE locationId = ?";
            jdbc.update(DELETE_SIGHTING, id);
            
            final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
            jdbc.update(DELETE_LOCATION, id);
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }
    
    @Override
    public List<Supe> supesAtLocation(int id) throws SuperSightingsPersistenceException {
        final String SELECT_SUPES_OF_LOCATION = "SELECT s.* FROM supe s JOIN supeSighting sl on supeId = s.id "
                + "JOIN sighting st ON sl.sightingID = st.id WHERE st.locationID = ?";
        try {
            List<Supe> supes = jdbc.query(SELECT_SUPES_OF_LOCATION, new SupeMapper(), id);
            return supes;
        } catch (Exception e) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }
    
    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setLatitude(rs.getBigDecimal("latitude").stripTrailingZeros());
            location.setLongitude(rs.getBigDecimal("longitude").stripTrailingZeros());
            location.setAddress(rs.getString("address"));
            location.setPostCode(rs.getString("postCode"));
            location.setCity(rs.getString("city"));
            location.setCountry(rs.getString("country"));

            return location;
        }
    }    
}
