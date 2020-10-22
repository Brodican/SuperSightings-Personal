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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author utkua
 */
@Component
public class MakeObjectUtility {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    SupeDao supeDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    // Technically no need for differences (as different IDs) but added just in case of
    // more granular assertions.
    public Location makeLocation(int difference) throws SuperSightingsPersistenceException {
        Location location = new Location();
        location.setName("name");
        location.setDescription("description");
        location.setLatitude(new BigDecimal("1.1" + difference));
        location.setLongitude(new BigDecimal("1.2"));
        location.setAddress("address");
        location.setPostCode("postCode");
        location.setCity("city");
        location.setCountry("country");
        // = isn't necessary, just for clarity.
        location = locationDao.addLocation(location);

        return location;
    }
    
    public Supe makeSupe(int difference) throws SuperSightingsPersistenceException {
        Supe supe = new Supe();
        supe.setFirstName("Test Supe First");
        supe.setLastName("Test Supe Last");
        supe.setDescription("Test Description" + difference);
        supe.setSuperPower("Test Power");
        supe = supeDao.addSupe(supe);
        return supe;
    }
    
    public Sighting makeSighting(List<Supe> supes, Location location) throws SuperSightingsPersistenceException {
        Sighting sighting = new Sighting();
        sighting.setSightingDate(LocalDateTime.now().withNano(0));
        sighting.setLocation(location);
        sighting.setSupes(supes);
        sighting = sightingDao.addSighting(sighting);

        return sighting;
    }
    
    public Organization makeOrganization(List<Supe> supes) throws SuperSightingsPersistenceException {
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
        organization = organizationDao.addOrganization(organization);
        
        return organization;
    }
}
