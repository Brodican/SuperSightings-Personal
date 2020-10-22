/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao;

import com.mthree.supersightings.dao.implementations.SuperSightingsPersistenceException;
import com.mthree.supersightings.entities.Location;
import com.mthree.supersightings.entities.Supe;
import java.util.List;

/**
 * DAO for Location data access, Separation of Concerns.
 * 
 * @author utkua
 */
public interface LocationDao {

    /**
     * Retrieve location row with given ID from DB, converts
     * to Location object an returns.
     * 
     * @param id
     * @return
     * @throws SuperSightingsPersistenceException
     */
    Location getLocationById(int id) throws SuperSightingsPersistenceException;

    /**
     * Convert entire Location table to list of Location objects.
     * 
     * @return
     * @throws SuperSightingsPersistenceException
     */
    List<Location> getAllLocations() throws SuperSightingsPersistenceException;

    /**
     * Convert given Location object into values to be inserted as a tuple to the Location
     * table.
     * 
     * @param location
     * @return
     * @throws SuperSightingsPersistenceException
     */
    Location addLocation(Location location) throws SuperSightingsPersistenceException;

    /**
     * Update tuple with id of given Location object,
     * with values in the given Location object.
     * 
     * @param location
     * @throws SuperSightingsPersistenceException
     */
    void updateLocation(Location location) throws SuperSightingsPersistenceException;

    /**
     * Delete location tuple with given id from Location table.
     * 
     * @param id
     * @throws SuperSightingsPersistenceException
     */
    void deleteLocationById(int id) throws SuperSightingsPersistenceException;
    
    /**
     * Get supes which have been sighted at the location with the given id, as a list of Supe
     * objects.
     * 
     * @param id
     * @return
     * @throws SuperSightingsPersistenceException
     */
    List<Supe> supesAtLocation(int id) throws SuperSightingsPersistenceException;
}
