/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao.implementations;

import com.mthree.supersightings.dao.LocationDao;
import com.mthree.supersightings.dao.OrganizationDao;
import com.mthree.supersightings.dao.SightingDao;
import com.mthree.supersightings.dao.SupeDao;
import com.mthree.supersightings.entities.Location;
import com.mthree.supersightings.entities.Sighting;
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
public class SightingDaoDBTest {
    
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
    
    public SightingDaoDBTest() {
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
    public void testAddAndGetSighting() {
        try {
            Location location = makeObjectUtility.makeLocation(1);
            
            Supe supe = new Supe();
            supe.setFirstName("Test Supe First");
            supe.setLastName("Test Supe Last");
            supe.setDescription("Test Description");
            supe.setSuperPower("Test Power");
            supe = supeDao.addSupe(supe);
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            
            Sighting sighting = new Sighting();
            sighting.setSightingDate(LocalDateTime.now().withNano(0));
            sighting.setLocation(location);
            sighting.setSupes(supes);
            sighting = sightingDao.addSighting(sighting);
            
            Sighting fromDao = sightingDao.getSightingById(sighting.getId());
            assertEquals(sighting.getSightingDate(), fromDao.getSightingDate());
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }

    @Test
    public void testGetAllSightings() {
        try {
            Location location = makeObjectUtility.makeLocation(1);
            
            Supe supe = makeObjectUtility.makeSupe(1);
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            
            Sighting sighting = makeObjectUtility.makeSighting(supes, location);
            
            Sighting sighting2 = makeObjectUtility.makeSighting(supes, location);
            
            List<Sighting> sightings = sightingDao.getAllSightings();
            assertEquals(2, sightings.size());
            assertTrue(sightings.contains(sighting));
            assertTrue(sightings.contains(sighting2));
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }
    
    @Test
    public void testGetTenMostRecentSightings() {
        try {
            for (int i = 0; i < 15; i++) {
                addSighting(i);
            }
            
            List<Sighting> sightings = sightingDao.getTenMostRecentSightings();
            assertEquals(10, sightings.size());
            assertTrue(sightings.get(0).getSightingDate().compareTo(sightings.get(1).getSightingDate()) > 0);
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }
    
    private Sighting addSighting(int dateEnd) throws SuperSightingsPersistenceException {
        Location location = makeObjectUtility.makeLocation(1);
        
        Supe supe = makeObjectUtility.makeSupe(1);
        List<Supe> supes = new ArrayList<>();
        supes.add(supe);
        
        String dateEndString = String.format("%02d" , dateEnd);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(LocalDateTime.parse("2015-02-20T06:30:" + dateEndString));
        sighting.setLocation(location);
        sighting.setSupes(supes);
        sighting = sightingDao.addSighting(sighting);
        
        return sighting;
    }

    @Test
    public void testUpdateSighting() {
        try {
            Location location = makeObjectUtility.makeLocation(1);
            
            Supe supe = makeObjectUtility.makeSupe(1);
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            
            Sighting sighting = makeObjectUtility.makeSighting(supes, location);
            
            Sighting fromDao = sightingDao.getSightingById(sighting.getId());
            assertEquals(sighting.getSightingDate(), fromDao.getSightingDate());
            assertEquals(sighting, fromDao);
            
            sighting.setSightingDate(LocalDateTime.parse("2015-02-20T06:30:03"));
            Supe supe2 = makeObjectUtility.makeSupe(2);
            supes.add(supe2);
            sighting.setSupes(supes);
            
            sightingDao.updateSighting(sighting);
            
            assertNotEquals(sighting, fromDao);
            
            fromDao = sightingDao.getSightingById(sighting.getId());
            assertEquals(sighting, fromDao);
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteSightingById() {
        Sighting fromDao;
         Sighting sighting;
        try {
            Location location = makeObjectUtility.makeLocation(1);
            
            Supe supe = makeObjectUtility.makeSupe(1);
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            
            sighting = makeObjectUtility.makeSighting(supes, location);
            
            fromDao = sightingDao.getSightingById(sighting.getId());
            assertEquals(sighting, fromDao);
            
            sightingDao.deleteSightingById(sighting.getId());
        } catch (SuperSightingsPersistenceException ex) {
            fail();
            return;
        } try {
            fromDao = sightingDao.getSightingById(sighting.getId());
            assertNull(fromDao);
        } catch (SuperSightingsPersistenceException ex) {
            assertEquals("Data access issue (SQL)", ex.getMessage());
        }
    }

    @Test
    public void testGetSightingsForLocation() {
        try {
            Location location = makeObjectUtility.makeLocation(1);
            
            Location location2 = makeObjectUtility.makeLocation(2);
            
            Supe supe = makeObjectUtility.makeSupe(1);
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            
            Sighting sighting = makeObjectUtility.makeSighting(supes, location);
            
            Sighting sighting2 = makeObjectUtility.makeSighting(supes, location2);
            
            Sighting sighting3 = makeObjectUtility.makeSighting(supes, location);
            
            List<Sighting> sightings = sightingDao.getSightingsForLocation(location);
            
            assertEquals(2, sightings.size());
            assertTrue(sightings.contains(sighting));
            assertFalse(sightings.contains(sighting2));
            assertTrue(sightings.contains(sighting3));
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }

    @Test
    public void testGetSightingsForSupe() {
        try {
            Location location = makeObjectUtility.makeLocation(1);
            
            Supe supe = makeObjectUtility.makeSupe(1);
            
            Supe supe2 = makeObjectUtility.makeSupe(2);
            
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            supes.add(supe2);
            
            Sighting sighting = makeObjectUtility.makeSighting(supes, location);
            
            List<Supe> supes2 = new ArrayList<>();
            supes2.add(supe2);
            Sighting sighting2 = makeObjectUtility.makeSighting(supes2, location);
            
            Sighting sighting3 = makeObjectUtility.makeSighting(supes, location);
            
            List<Sighting> sightings = sightingDao.getSightingsForSupe(supe);
            assertEquals(2, sightings.size());
            assertTrue(sightings.contains(sighting));
            assertFalse(sightings.contains(sighting2));
            assertTrue(sightings.contains(sighting3));
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }
    
    public void testGetSightingsForDate() {
        try {
            Location location = makeObjectUtility.makeLocation(1);
            Supe supe = makeObjectUtility.makeSupe(1);
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            Sighting sighting = makeObjectUtility.makeSighting(supes, location);

            sighting.setSightingDate(LocalDateTime.parse("12-12-2020T12:20"));
            sighting = sightingDao.addSighting(sighting);

            List<Sighting> sightingsAtDate = sightingDao.getSightingsForDate(LocalDateTime.parse("12-12-2020T12:20"));
            assertEquals(sighting, sightingsAtDate.get(0));
        } catch (SuperSightingsPersistenceException superSightingsPersistenceException) {
            fail();
        }
    }
    
}
