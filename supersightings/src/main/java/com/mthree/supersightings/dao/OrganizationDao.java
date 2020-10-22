/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao;

import com.mthree.supersightings.dao.implementations.SuperSightingsPersistenceException;
import com.mthree.supersightings.entities.Organization;
import com.mthree.supersightings.entities.Supe;
import java.util.List;

/**
 *
 * @author utkua
 */
public interface OrganizationDao {

    /**
     * Retrieve organization row with given ID from DB, converts
     * to Organization object an returns.
     * 
     * @param id
     * @return
     * @throws SuperSightingsPersistenceException
     */
    Organization getOrganizationById(int id) throws SuperSightingsPersistenceException;

    /**
     * Convert entire Organization table to list of Organization objects.
     * 
     * @return
     * @throws SuperSightingsPersistenceException
     */
    List<Organization> getAllOrganizations() throws SuperSightingsPersistenceException;

    /**
     * Convert given Organization object into values to be inserted as a tuple to the Organization
     * table, inserts them.
     * 
     * @param organization
     * @return
     * @throws SuperSightingsPersistenceException
     */
    Organization addOrganization(Organization organization) throws SuperSightingsPersistenceException;

    /**
     * Update tuple with id of given Organization object,
     * with values in the given Organization object.
     * 
     * @param organization
     * @throws SuperSightingsPersistenceException
     */
    void updateOrganization(Organization organization) throws SuperSightingsPersistenceException;

    /**
     * Delete Organization tuple with given id from Organization table.
     * 
     * @param id
     * @throws SuperSightingsPersistenceException
     */
    void deleteOrganizationById(int id) throws SuperSightingsPersistenceException;
    
    /**
     * Gets Organizations which Supe with given id belongs to from Organization table, 
     * as a list of Organization objects.
     * 
     * @param supe
     * @return
     * @throws SuperSightingsPersistenceException
     */
    List<Organization> getOrganizationsForSupe(int id) throws SuperSightingsPersistenceException;
}
