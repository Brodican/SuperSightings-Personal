/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.controllers;

import com.mthree.supersightings.dao.LocationDao;
import com.mthree.supersightings.dao.implementations.SuperSightingsPersistenceException;
import com.mthree.supersightings.entities.Location;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author utkua
 */
@Controller
public class LocationController {
    
    Set<ConstraintViolation<Location>> violations = new HashSet<>();
    
    @Autowired
    LocationDao locationDao;
    
    @GetMapping("locations")
    public String displayLocations(Model model) {
        try {
            List locations = locationDao.getAllLocations();
            model.addAttribute("locations", locations);
            model.addAttribute("errors", violations);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        return "locations";
    }
    
    // No particular reason for this vs request
    @PostMapping("addLocation")
    public String addLocation(String name, String description, BigDecimal latitude, BigDecimal longitude, String address, String postCode, String city, String country, Model model) {
        Location location = new Location();
            location.setName(name);
            location.setDescription(description);
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            location.setAddress(address);
            location.setPostCode(postCode);
            location.setCity(city);
            location.setCountry(country);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        
        if (violations.isEmpty()) {
            try {
                locationDao.addLocation(location);
            } catch (SuperSightingsPersistenceException ex) {
                model.addAttribute("error", ex);
            }
        }
        
        return "redirect:/locations";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id, Model model) {
        model.addAttribute("confirmDelete", "/deleteConfirmationLoc");
        model.addAttribute("cancel", "/locations");
        model.addAttribute("itemId", id);
        return "deleteConfirmation";
    }
    
    @GetMapping("deleteConfirmationLoc")
    public String deleteConfirm(Integer id, Model model) {
        try {
            locationDao.deleteLocationById(id);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        try {
            Location location = locationDao.getLocationById(id);
            model.addAttribute("location", location);
            model.addAttribute("errors", violations);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String performEditLocation(Location location, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        
        if (violations.isEmpty()) {
            try {
                locationDao.updateLocation(location);
            } catch (SuperSightingsPersistenceException ex) {
                model.addAttribute("error", ex);
            }
        } else {
            model.addAttribute("errors", violations);
            return "editLocation";
        }
        
        return "redirect:/locations";
    }
}
