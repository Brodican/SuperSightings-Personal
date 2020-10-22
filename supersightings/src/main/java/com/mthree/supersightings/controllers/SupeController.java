/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.controllers;

import com.mthree.supersightings.dao.SupeDao;
import com.mthree.supersightings.dao.implementations.SuperSightingsPersistenceException;
import com.mthree.supersightings.entities.Supe;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author utkua
 */
@Controller
public class SupeController {

    Set<ConstraintViolation<Supe>> violations = new HashSet<>();
    
    @Autowired
    SupeDao supeDao;
   
    @GetMapping("supes")
    public String displaySupes(Model model) {
        try {
            List supes = supeDao.getAllSupes();
            model.addAttribute("supes", supes);
            model.addAttribute("errors", violations);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        return "supes";
    }
    
    @PostMapping("addSupe")
    public String addSupe(String firstName, String lastName, String description, String superPower, Model model) {
        Supe supe = new Supe();
        supe.setFirstName(firstName);
        supe.setLastName(lastName);
        supe.setDescription(description);
        supe.setSuperPower(superPower);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(supe);
        
        if (violations.isEmpty()) {
            try {
                supe = supeDao.addSupe(supe);
            } catch (SuperSightingsPersistenceException ex) {
                model.addAttribute("error", ex);
            }
        }
        
        return "redirect:/supes";
    }
    
    @GetMapping("deleteSupe")
    public String deleteSupe(Integer id, Model model) {
        model.addAttribute("confirmDelete", "/deleteConfirmationSupe");
        model.addAttribute("cancel", "/supes");
        model.addAttribute("itemId", id);
        return "deleteConfirmation";
    }
    
    @GetMapping("deleteConfirmationSupe")
    public String deleteConfirm(Integer id, Model model) {
        try {
            supeDao.deleteSupeById(id);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        return "redirect:/supes";
    }
    
    @GetMapping("editSupe")
    public String editSupe(Integer id, Model model) {
        try {
            Supe supe = supeDao.getSupeById(id);
            model.addAttribute("supe", supe);
            model.addAttribute("errors", violations);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        return "editSupe";
    }
    
    // If a validation error occurs, Spring automatically fills in the BindingResult
    @PostMapping("editSupe")
    public String performEditSupe(Supe supe, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(supe);
        
        if (violations.isEmpty()) {
            try {
                supeDao.updateSupe(supe);
            } catch (SuperSightingsPersistenceException ex) {
                model.addAttribute("error", ex);
            }
        } else {
            model.addAttribute("errors", violations);
            return "editSupe";
        }
        
        return "redirect:/supes";
    }
}
