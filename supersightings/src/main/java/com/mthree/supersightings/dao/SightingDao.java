/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao;

import com.mthree.supersightings.dao.implementations.SuperSightingsPersistenceException;
import com.mthree.supersightings.entities.Sighting;
import com.mthree.supersightings.entities.Location;
import com.mthree.supersightings.entities.Supe;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  DAO for Sighting data access, Separation of Concerns.
 * 
 * @author utkua
 */
public interface SightingDao {

    /**
     * Creates Sighting object from sighting table row with given ID.
     * 
     * @param id
     * @return Sighting object created from sighting data stored in row with given ID.
     * @throws SuperSightingsPersistenceException
     */
    Sighting getSightingById(int id) throws SuperSightingsPersistenceException;

    /**
     * Convert entire Sighting table to list of Sighting objects.
     * 
     * @return
     * @throws SuperSightingsPersistenceException
     */
    List<Sighting> getAllSightings() throws SuperSightingsPersistenceException;

    /**
     * Convert given Sighting object into values to be inserted as a tuple to the Sighting
     * table, inserts them.
     * 
     * @param sighting
     * @return
     * @throws SuperSightingsPersistenceException
     */
    Sighting addSighting(Sighting sighting) throws SuperSightingsPersistenceException;

    /**
     * 
     * @param sighting
     * @throws SuperSightingsPersistenceException
     */
    void updateSighting(Sighting sighting) throws SuperSightingsPersistenceException;

    /**
     *
     * @param id
     * @throws SuperSightingsPersistenceException
     */
    void deleteSightingById(int id) throws SuperSightingsPersistenceException;
    
    /**
     *
     * @return
     * @throws SuperSightingsPersistenceException
     */
    List<Sighting> getTenMostRecentSightings() throws SuperSightingsPersistenceException;

    /**
     *
     * @param location
     * @return
     * @throws SuperSightingsPersistenceException
     */
    List<Sighting> getSightingsForLocation(Location location) throws SuperSightingsPersistenceException;

    /**
     *
     * @param supe
     * @return
     * @throws SuperSightingsPersistenceException
     */
    List<Sighting> getSightingsForSupe(Supe supe) throws SuperSightingsPersistenceException;
    
    /**
     *
     * @param dateTime
     * @return
     * @throws SuperSightingsPersistenceException
     */
    List<Sighting> getSightingsForDate(LocalDateTime dateTime) throws SuperSightingsPersistenceException;
}
