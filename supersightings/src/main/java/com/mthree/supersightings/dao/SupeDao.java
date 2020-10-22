/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao;

import com.mthree.supersightings.dao.implementations.SuperSightingsPersistenceException;
import com.mthree.supersightings.entities.Location;
import com.mthree.supersightings.entities.Organization;
import com.mthree.supersightings.entities.Supe;
import java.util.List;

/**
 *
 * @author utkua
 */
public interface SupeDao {

    /**
     * Creates Supe object from supe table row with given ID.
     * 
     * @param id
     * @return Supe object created from Supe data stored in row with given ID.
     * @throws SuperSightingsPersistenceException
     */
    Supe getSupeById(int id) throws SuperSightingsPersistenceException;

    /**
     * Retrives a List of all Supe objects.
     * 
     * @return
     * @throws SuperSightingsPersistenceException
     */
    List<Supe> getAllSupes() throws SuperSightingsPersistenceException;

    /**
     * Adds the given Supe to the supe table.
     * 
     * @param supe
     * @return
     * @throws SuperSightingsPersistenceException
     */
    Supe addSupe(Supe supe) throws SuperSightingsPersistenceException;

    /**
     * Updates supe table row with given Supe ID, with given Supe details.
     * 
     * @param supe
     * @throws SuperSightingsPersistenceException
     */
    void updateSupe(Supe supe) throws SuperSightingsPersistenceException;

    /**
     * Deletes Supe with given ID from supe table.
     * 
     * @param id
     * @throws SuperSightingsPersistenceException
     */
    void deleteSupeById(int id) throws SuperSightingsPersistenceException;
    
    /**
     * Gets organizations of Supe with given ID from organization table, as a list of Organization objects.
     * 
     * @param id
     * @return 
     * @throws SuperSightingsPersistenceException
     */
    List<Organization> getSupeOrganizations(int id) throws SuperSightingsPersistenceException;
    
    /**
     * Gets locations of Supe with given ID from location table, as a list of Location objects.
     * 
     * @param id
     * @return 
     * @throws SuperSightingsPersistenceException
     */
    List<Location> getSupeLocations(int id) throws SuperSightingsPersistenceException;
}
