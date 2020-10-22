/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.controllers;

import com.mthree.supersightings.dao.LocationDao;
import com.mthree.supersightings.dao.SightingDao;
import com.mthree.supersightings.dao.SupeDao;
import com.mthree.supersightings.dao.implementations.SuperSightingsPersistenceException;
import com.mthree.supersightings.entities.Location;
import com.mthree.supersightings.entities.Sighting;
import com.mthree.supersightings.entities.Supe;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author utkua
 */
@Controller
public class SightingController {

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
    
    @Autowired
    LocationDao locationDao;

    @Autowired
    SupeDao supeDao;

    @Autowired
    SightingDao sightingDao;
   
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        try {
            List<Sighting> sightings = sightingDao.getAllSightings();
            List<Location> locations = locationDao.getAllLocations();
            List<Supe> supes = supeDao.getAllSupes();
            model.addAttribute("sightings", sightings);
            model.addAttribute("locations", locations);
            model.addAttribute("supes", supes);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("errors", violations);
        }
        return "sightings";
    }
    
    @PostMapping("addSighting")
    public String addSighting(String sightingDate, HttpServletRequest request, Model model) {
        String locationId = request.getParameter("locationId");
        String[] supeIds = request.getParameterValues("supeId");
        
        LocalDateTime date = LocalDateTime.parse(sightingDate);
        Sighting sighting = new Sighting();
        sighting.setSightingDate(date);
        
        try {
            sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("errors", ex);
        }
        
        List<Supe> supes = new ArrayList<>();
        for(String supeId : supeIds) {
            try {
                supes.add(supeDao.getSupeById(Integer.parseInt(supeId)));
            } catch (SuperSightingsPersistenceException ex) {
                model.addAttribute("errors", ex);
            }
        }
        
        sighting.setSupes(supes);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        
        if (violations.isEmpty()) {
            try {
                sightingDao.addSighting(sighting);
            } catch (SuperSightingsPersistenceException ex) {
                model.addAttribute("errors", ex);
            }
        }
        
        return "redirect:/sightings";
    }
    
    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) throws SuperSightingsPersistenceException {
        try {
            Sighting sighting = sightingDao.getSightingById(id);
            model.addAttribute("sighting", sighting);
        } catch (SuperSightingsPersistenceException superSightingsPersistenceException) {
            model.addAttribute("errors", superSightingsPersistenceException);
        }
        return "sightingDetail";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id, Model model) {
        model.addAttribute("confirmDelete", "/deleteConfirmationSight");
        model.addAttribute("cancel", "/sightings");
        model.addAttribute("itemId", id);
        return "deleteConfirmation";
    }
    
    @GetMapping("deleteConfirmationSight")
    public String deleteConfirm(Integer id, Model model) {
        try {
            sightingDao.deleteSightingById(id);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        try {
            Sighting sighting = sightingDao.getSightingById(id);
            List<Supe> supes = supeDao.getAllSupes();
            List<Location> locations = locationDao.getAllLocations();
            model.addAttribute("sighting", sighting);
            model.addAttribute("supes", supes);
            model.addAttribute("locations", locations);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("errors", ex);
        }
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String performEditSighting(String sightingDate, HttpServletRequest request, Model model) {
        try {
            String locationId = request.getParameter("locationId");
            String[] supeIds = request.getParameterValues("supeId");
            
            LocalDateTime date = LocalDateTime.parse(sightingDate);
            Sighting sighting = new Sighting();
            sighting.setSightingDate(date);
            sighting.setId(Integer.parseInt(request.getParameter("id")));
            
            sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
            
            List<Supe> supes = new ArrayList<>();
            for (String supeId : supeIds) {
                supes.add(supeDao.getSupeById(Integer.parseInt(supeId)));
            }
            
            sighting.setSupes(supes);
            
            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
            violations = validate.validate(sighting);
            if (violations.isEmpty()) {
                sightingDao.updateSighting(sighting);
            }
            
        } catch (NumberFormatException numberFormatException) {
            model.addAttribute("errors", numberFormatException);
        } catch (SuperSightingsPersistenceException superSightingsPersistenceException) {
            model.addAttribute("errors", superSightingsPersistenceException);
        }
        
        return "redirect:/sightings";
    }
}
