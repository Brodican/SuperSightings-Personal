/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao.implementations;

import com.mthree.supersightings.entities.Location;
import com.mthree.supersightings.entities.Organization;
import com.mthree.supersightings.entities.Sighting;
import com.mthree.supersightings.entities.Location;
import com.mthree.supersightings.dao.LocationDao;
import com.mthree.supersightings.dao.OrganizationDao;
import com.mthree.supersightings.dao.SightingDao;
import com.mthree.supersightings.dao.SupeDao;
import com.mthree.supersightings.entities.Supe;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author utkua
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    SupeDao supeDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    MakeObjectUtility makeObjectUtility;
    
    public LocationDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        try {
            List<Location> locations = locationDao.getAllLocations();
            for(Location location : locations) {
                locationDao.deleteLocationById(location.getId());
            }
            
            List<Supe> supes = supeDao.getAllSupes();
            for(Supe supe : supes) {
                supeDao.deleteSupeById(supe.getId());
            }
            
            List<Sighting> sightings = sightingDao.getAllSightings();
            for(Sighting sighting : sightings) {
                sightingDao.deleteSightingById(sighting.getId());
            }
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetLocation() {
        try {
            Location location = makeObjectUtility.makeLocation(1);
            
            Location fromDao = locationDao.getLocationById(location.getId());
            
            assertEquals(location.getAddress(), fromDao.getAddress());
            assertEquals(location.getCity(), fromDao.getCity());
            assertEquals(location.getCountry(), fromDao.getCountry());
            assertEquals(location.getPostCode(), fromDao.getPostCode());
            assertEquals(location.getName(), fromDao.getName());
            assertEquals(location.getLongitude(), fromDao.getLongitude());
            assertEquals(location.getLatitude(), fromDao.getLatitude());
            assertEquals(location.getId(), fromDao.getId());
            assertEquals(location.getDescription(), fromDao.getDescription());
            
            assertEquals(location, fromDao);
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }

    @Test
    public void testGetAllLocations() {
        try {
            Location location = makeObjectUtility.makeLocation(1);
            
            Location location2 = makeObjectUtility.makeLocation(2);
            
            List<Location> locations = locationDao.getAllLocations();
            
            assertEquals(2, locations.size());
            assertTrue(locations.contains(location));
            assertTrue(locations.contains(location2));
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
        
    }

    @Test
    public void testUpDateLocation() {
        try {
            Location location = makeObjectUtility.makeLocation(1);
            
            Location fromDao = locationDao.getLocationById(location.getId());
            assertEquals(location, fromDao);
            
            location.setName("New Test Location Name");
            locationDao.updateLocation(location);
            
            assertNotEquals(location, fromDao);
            
            fromDao = locationDao.getLocationById(location.getId());
            
            assertEquals(location, fromDao);
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteLocationById() {
        Location fromDao;
        Location location;
        try {
            location = makeObjectUtility.makeLocation(1);
            
            Supe supe = new Supe();
            supe.setFirstName("Test Supe First");
            supe.setLastName("Test Supe Last");
            supe.setDescription("Test Description");
            supe.setSuperPower("Test Power");
            supe = supeDao.addSupe(supe);
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            
            Sighting sighting = new Sighting();
            sighting.setSightingDate(LocalDateTime.now());
            sighting.setLocation(location);
            sighting.setSupes(supes);
            sighting = sightingDao.addSighting(sighting);
            
            fromDao = locationDao.getLocationById(location.getId());
            assertEquals(location, fromDao);
            
            locationDao.deleteLocationById(location.getId());
        } catch (SuperSightingsPersistenceException ex) {
            fail();
            return;
        }
        try {
            fromDao = locationDao.getLocationById(location.getId());
            assertNull(fromDao);
        } catch (SuperSightingsPersistenceException ex) {
            assertEquals("Data access issue (SQL)", ex.getMessage());
        }
    }
    
    @Test
    public void testSupesAtLocation() {
        try {
            Location location1 = makeObjectUtility.makeLocation(1);
            Location location2 = makeObjectUtility.makeLocation(2);
            Location location3 = makeObjectUtility.makeLocation(3);
            
            Supe supe1 = makeObjectUtility.makeSupe(1);
            Supe supe2 = makeObjectUtility.makeSupe(2);
            Supe supe3 = makeObjectUtility.makeSupe(3);
            
            List<Supe> supes1 = new ArrayList<>();
            supes1.add(supe1);
            
            List<Supe> supes2 = new ArrayList<>();
            supes2.add(supe2);
            
            Sighting sighting1 = makeObjectUtility.makeSighting(supes1, location1);
            Sighting sighting2 = makeObjectUtility.makeSighting(supes2, location1);
            List<Supe> supesAtLocation = locationDao.supesAtLocation(location1.getId());
            assertEquals(2, supesAtLocation.size());
            assertEquals(supe1, supesAtLocation.get(0));
            assertEquals(supe2, supesAtLocation.get(1));
        } catch (SuperSightingsPersistenceException superSightingsPersistenceException) {
            fail();
        }
    }
    
}
