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
import com.mthree.supersightings.entities.Organization;
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
public class SupeDaoDBTest {
    
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
    
    public SupeDaoDBTest() {
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
    public void testAddAndGetSupe() {
        try {
            Supe supe = new Supe();
            supe.setFirstName("Test Supe First");
            supe.setLastName("Test Supe Last");
            supe.setDescription("Test Description");
            supe.setSuperPower("Test Power");
            supe = supeDao.addSupe(supe);
            
            Supe fromDao = supeDao.getSupeById(supe.getId());
            assertEquals(supe, fromDao);
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }

    @Test
    public void testGetAllSupes() {
        try {
            Supe supe = new Supe();
            supe.setFirstName("Test Supe First");
            supe.setLastName("Test Supe Last");
            supe.setDescription("Test Description");
            supe.setSuperPower("Test Power");
            supe = supeDao.addSupe(supe);
            
            Supe supe2 = new Supe();
            supe2.setFirstName("Test Supe First 2");
            supe2.setLastName("Test Supe Last 2");
            supe2.setDescription("Test Description 2");
            supe2.setSuperPower("Test Power 2");
            supe2 = supeDao.addSupe(supe2);
            
            List<Supe> supes = supeDao.getAllSupes();
            
            assertEquals(2, supes.size());
            assertTrue(supes.contains(supe));
            assertTrue(supes.contains(supe2));
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }

    @Test
    public void testUpDateSupe() {
        try {
            Supe supe = new Supe();
            supe.setFirstName("Test Supe First");
            supe.setLastName("Test Supe Last");
            supe.setDescription("Test Description");
            supe.setSuperPower("Test Power");
            supe = supeDao.addSupe(supe);
            
            Supe fromDao = supeDao.getSupeById(supe.getId());
            assertEquals(supe, fromDao);
            
            supe.setFirstName("New Test Supe First");
            supeDao.updateSupe(supe);
            
            assertNotEquals(supe, fromDao);
            
            fromDao = supeDao.getSupeById(supe.getId());
            
            assertEquals(supe, fromDao);
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteSupeById() {
        Supe supe;
        Supe fromDao;
        try {
            Location location = makeObjectUtility.makeLocation(1);
            
            supe = new Supe();
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
            
            Organization organization = new Organization();
            organization.setName("name");
            organization.setDescription("description");
            organization.setPhoneNumber("phoneNumber");
            organization.setEmail("email");
            organization.setAddress("address");
            organization.setPostCode("postCode");
            organization.setCity("city");
            organization.setCountry("country");
            organization.setSupes(supes);
            organizationDao.addOrganization(organization);
            
            fromDao = supeDao.getSupeById(supe.getId());
            assertEquals(supe, fromDao);
            
            supeDao.deleteSupeById(supe.getId());
        } catch (SuperSightingsPersistenceException ex) {
            fail();
            return;
        } try {
            fromDao = supeDao.getSupeById(supe.getId());
        } catch (Exception e) {
            assertEquals("Data access issue (SQL)", e.getMessage());
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
            supes1.add(supe2);
            
            List<Supe> supes2 = new ArrayList<>();
            supes2.add(supe2);
            
            makeObjectUtility.makeSighting(supes1, location1);
            makeObjectUtility.makeSighting(supes2, location2);
            
            List<Location> locationsOfSupe = supeDao.getSupeLocations(supe2.getId());
            assertEquals(2, locationsOfSupe.size());
            assertEquals(location1, locationsOfSupe.get(0));
            assertEquals(location2, locationsOfSupe.get(1));
        } catch (SuperSightingsPersistenceException superSightingsPersistenceException) {
            superSightingsPersistenceException.printStackTrace();
            fail();
        }
    }
    
}
