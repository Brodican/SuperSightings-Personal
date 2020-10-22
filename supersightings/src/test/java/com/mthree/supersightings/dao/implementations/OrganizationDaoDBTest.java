/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao.implementations;

import com.mthree.supersightings.dao.OrganizationDao;
import com.mthree.supersightings.dao.SightingDao;
import com.mthree.supersightings.dao.SupeDao;
import com.mthree.supersightings.entities.Organization;
import com.mthree.supersightings.entities.Supe;
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
public class OrganizationDaoDBTest {
    
    @Autowired
    SupeDao supeDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    MakeObjectUtility makeObjectUtility;
    
    public OrganizationDaoDBTest() {
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
            List<Supe> supes = supeDao.getAllSupes();
            for(Supe supe : supes) {
                supeDao.deleteSupeById(supe.getId());
            }
            
            List<Organization> organizations = organizationDao.getAllOrganizations();
            for(Organization organization : organizations) {
                organizationDao.deleteOrganizationById(organization.getId());
            }
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetOrganization() throws SuperSightingsPersistenceException {
        
        Supe supe = makeObjectUtility.makeSupe(1);
        List<Supe> supes = new ArrayList<>();
        supes.add(supe);
        Organization organization = makeObjectUtility.makeOrganization(supes);
        
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
    }

    @Test
    public void testGetAllOrganizations() {
        
        try {
            Supe supe = makeObjectUtility.makeSupe(1);
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            
            Organization organization = makeObjectUtility.makeOrganization(supes);
            
            Organization organization2 = makeObjectUtility.makeOrganization(supes);
            
            List<Organization> organizations = organizationDao.getAllOrganizations();
            assertEquals(2, organizations.size());
            assertTrue(organizations.contains(organization));
            assertTrue(organizations.contains(organization2));
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateOrganization() {
        
        try {
            Supe supe = makeObjectUtility.makeSupe(1);
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            
            Organization organization = makeObjectUtility.makeOrganization(supes);
            
            Organization fromDao = organizationDao.getOrganizationById(organization.getId());
            assertEquals(organization, fromDao);
            
            organization.setName("New Test Organization Name");
            Supe supe2 = makeObjectUtility.makeSupe(2);
            supes.add(supe2);
            organization.setSupes(supes);
            
            organizationDao.updateOrganization(organization);
            
            assertNotEquals(organization, fromDao);
            
            fromDao = organizationDao.getOrganizationById(organization.getId());
            assertEquals(organization, fromDao);
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteOrganizationById() throws SuperSightingsPersistenceException {
        
        Supe supe = makeObjectUtility.makeSupe(1);
        List<Supe> supes = new ArrayList<>();
        supes.add(supe);
        
        Organization organization = makeObjectUtility.makeOrganization(supes);
        
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
        
        organizationDao.deleteOrganizationById(organization.getId());
        
        try {
            fromDao = organizationDao.getOrganizationById(organization.getId());
        } catch (SuperSightingsPersistenceException e) {
            assertEquals("Data access issue (SQL)", e.getMessage());
        }
    }

    @Test
    public void testGetOrganizationsForSupe() {
        
        try {
            Supe supe = makeObjectUtility.makeSupe(1);
            
            Supe supe2 = makeObjectUtility.makeSupe(2);
            
            List<Supe> supes = new ArrayList<>();
            supes.add(supe);
            supes.add(supe2);
            
            List<Supe> supes2 = new ArrayList<>();
            supes2.add(supe2);
            
            Organization organization = makeObjectUtility.makeOrganization(supes);
            
            Organization organization2 = makeObjectUtility.makeOrganization(supes2);
            
            Organization organization3 = makeObjectUtility.makeOrganization(supes);
            
            List<Organization> organizations = organizationDao.getOrganizationsForSupe(supe.getId());
            assertEquals(2, organizations.size());
            assertTrue(organizations.contains(organization));
            assertFalse(organizations.contains(organization2));
            assertTrue(organizations.contains(organization3));
        } catch (SuperSightingsPersistenceException ex) {
            fail();
        }
    }
    
}
