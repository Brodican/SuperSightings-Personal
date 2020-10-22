/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao.implementations;

import com.mthree.supersightings.dao.SightingDao;
import com.mthree.supersightings.dao.implementations.LocationDaoDB.LocationMapper;
import com.mthree.supersightings.dao.implementations.SupeDaoDB.SupeMapper;
import com.mthree.supersightings.entities.Location;
import com.mthree.supersightings.entities.Sighting;
import com.mthree.supersightings.entities.Supe;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) throws SuperSightingsPersistenceException {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setLocation(getLocationForSighting(id));
            sighting.setSupes(getSupesForSighting(id));
            return sighting;
        } catch(DataAccessException ex) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT t.* FROM location t "
                + "JOIN sighting c ON c.locationId = t.id WHERE c.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    private List<Supe> getSupesForSighting(int id) {
        final String SELECT_STUDENTS_FOR_SIGHTING = "SELECT s.* FROM supe s "
                + "JOIN supeSighting cs ON cs.supeId = s.id WHERE cs.sightingId = ?";
        return jdbc.query(SELECT_STUDENTS_FOR_SIGHTING, new SupeMapper(), id);
    }

    @Override
    public List<Sighting> getAllSightings() throws SuperSightingsPersistenceException {
        try {
            final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
            List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
            associateLocationAndSupes(sightings);
            return sightings;
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    private void associateLocationAndSupes(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
            sighting.setSupes(getSupesForSighting(sighting.getId()));
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) throws SuperSightingsPersistenceException {
        try {
            final String INSERT_SIGHTING = "INSERT INTO sighting(sightingDate, locationId) "
                    + "VALUES(?,?)";
            jdbc.update(INSERT_SIGHTING,
                    Timestamp.valueOf(sighting.getSightingDate()),
                    sighting.getLocation().getId());
            
            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            sighting.setId(newId);
            insertSightingSupe(sighting);
            return sighting;
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
            
        }
    }

    private void insertSightingSupe(Sighting sighting) {
        
        final String INSERT_SIGHTING_STUDENT = "INSERT INTO "
                + "supeSighting(sightingId, supeId) VALUES(?,?)";
        for(Supe supe : sighting.getSupes()) {
            jdbc.update(INSERT_SIGHTING_STUDENT, 
                    sighting.getId(),
                    supe.getId());
        }
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) throws SuperSightingsPersistenceException {
        try {
            final String UPDATE_SIGHTING = "UPDATE sighting SET sightingDate = ?, "
                    + "locationId = ? WHERE id = ?";
            jdbc.update(UPDATE_SIGHTING,
                    sighting.getSightingDate(),
                    sighting.getLocation().getId(),
                    sighting.getId());
            
            final String DELETE_SIGHTING_STUDENT = "DELETE FROM supeSighting WHERE sightingId = ?";
            jdbc.update(DELETE_SIGHTING_STUDENT, sighting.getId());
            insertSightingSupe(sighting);
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) throws SuperSightingsPersistenceException {
        try {
            final String DELETE_SIGHTING_STUDENT = "DELETE FROM supeSighting WHERE sightingId = ?";
            jdbc.update(DELETE_SIGHTING_STUDENT, id);
            
            final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
            jdbc.update(DELETE_SIGHTING, id);
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    public List<Sighting> getSightingsForLocation(Location location) throws SuperSightingsPersistenceException {
        try {
            final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sighting WHERE locationId = ?";
            List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION,
                    new SightingMapper(), location.getId());
            associateLocationAndSupes(sightings);
            return sightings;
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    public List<Sighting> getSightingsForSupe(Supe supe) throws SuperSightingsPersistenceException {
        try {
            final String SELECT_SIGHTINGS_FOR_STUDENT = "SELECT c.* FROM sighting c JOIN "
                    + "supeSighting cs ON cs.sightingId = c.Id WHERE cs.supeId = ?";
            List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_STUDENT,
                    new SightingMapper(), supe.getId());
            associateLocationAndSupes(sightings);
            return sightings;
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }

    @Override
    public List<Sighting> getTenMostRecentSightings() throws SuperSightingsPersistenceException {
        try {
            final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting ORDER BY sightingDate DESC";
            List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
            sightings = sightings.stream().limit(10).collect(Collectors.toList());
            associateLocationAndSupes(sightings);
            return sightings;
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }
    
    @Override
    public List<Sighting> getSightingsForDate(LocalDateTime dateTime) throws SuperSightingsPersistenceException {
        try {
            final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting WHERE date = ?";
            List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper(), dateTime);
            associateLocationAndSupes(sightings);
            return sightings;
        } catch (DataAccessException dataAccessException) {
            throw new SuperSightingsPersistenceException("Data access issue (SQL)");
        }
    }
   
    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setSightingDate(rs.getTimestamp("sightingDate").toLocalDateTime().withNano(0));
            return sighting;
        }
    }
}
