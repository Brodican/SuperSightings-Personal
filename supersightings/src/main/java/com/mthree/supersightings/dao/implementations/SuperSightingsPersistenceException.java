/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.dao.implementations;

/**
 *
 * @author utkua
 */
public class SuperSightingsPersistenceException extends Exception {
    
    public SuperSightingsPersistenceException(String message) {
        super(message);
    }

    public SuperSightingsPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
