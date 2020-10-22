/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.controllers;

import com.mthree.supersightings.dao.OrganizationDao;
import com.mthree.supersightings.dao.SightingDao;
import com.mthree.supersightings.dao.SupeDao;
import com.mthree.supersightings.dao.implementations.SuperSightingsPersistenceException;
import com.mthree.supersightings.entities.Organization;
import com.mthree.supersightings.entities.Supe;
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
public class OrganizationController {
    
    Set<ConstraintViolation<Organization>> violations = new HashSet<>();
    
    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SupeDao supeDao;

    @Autowired
    SightingDao sightingDao;
   
   
    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        try {
            List<Organization> organizations = organizationDao.getAllOrganizations();
            List<Supe> supes = supeDao.getAllSupes();
            model.addAttribute("organizations", organizations);
            model.addAttribute("supes", supes);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        model.addAttribute("errors", violations);
        
        return "organizations";
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request, Model model) {
        String[] supeIds = request.getParameterValues("supeId");
        
        try {
            List<Supe> supes = new ArrayList<>();
            for(String supeId : supeIds) {
                supes.add(supeDao.getSupeById(Integer.parseInt(supeId)));
            }
            organization.setSupes(supes);

            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
            violations = validate.validate(organization);

            if (violations.isEmpty()) {
                organizationDao.addOrganization(organization);
            }
            
        } catch (Exception e) {
            model.addAttribute("error", e);
        }
  
        return "redirect:/organizations";
    }
    
    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) throws SuperSightingsPersistenceException {
        Organization organization;
        try {
            organization = organizationDao.getOrganizationById(id);
            model.addAttribute("organization", organization);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        
        return "organizationDetail";
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id, Model model) {
        model.addAttribute("confirmDelete", "/deleteConfirmation");
        model.addAttribute("cancel", "/organizations");
        model.addAttribute("itemId", id);
        return "deleteConfirmation";
    }
    
    @GetMapping("deleteConfirmation")
    public String deleteConfirm(Integer id, Model model) {
        try {
            organizationDao.deleteOrganizationById(id);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        return "redirect:/organizations";
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) throws SuperSightingsPersistenceException {
        Organization organization = organizationDao.getOrganizationById(id);
        List<Supe> supes = null;
        try {
            supes = supeDao.getAllSupes();
        } catch (SuperSightingsPersistenceException superSightingsPersistenceException) {
            model.addAttribute("error", superSightingsPersistenceException);
        }
        model.addAttribute("organization", organization);
        model.addAttribute("supes", supes);
        model.addAttribute("errors", violations);
        return "editOrganization";
    }
    
    @PostMapping("editOrganization")
    public String performEditOrganization(Organization organization, HttpServletRequest request, Model model) throws SuperSightingsPersistenceException {
        String[] supeIds = request.getParameterValues("supeId");
        
        try {
            List<Supe> supes = new ArrayList<>();
            for (String supeId : supeIds) {
                supes.add(supeDao.getSupeById(Integer.parseInt(supeId)));
            }
            organization.setSupes(supes);
            
            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
            violations = validate.validate(organization);
            
            if (violations.isEmpty()) {
                organizationDao.updateOrganization(organization);
            } else {
                model.addAttribute("errors", violations);
                return "editOrganization";
            }
        } catch (NumberFormatException numberFormatException) {
            model.addAttribute("error", numberFormatException);
        } catch (SuperSightingsPersistenceException superSightingsPersistenceException) {
            model.addAttribute("error", superSightingsPersistenceException);
        }
        
        return "redirect:/organizations";
    }
   
}
