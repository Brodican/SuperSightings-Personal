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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author utkua
 */
@Controller
public class IndexController {

    @Autowired
    SightingDao sightingDao;
   
    @GetMapping("/homes")
    public String displaySightings(Model model) {
        try {
            List<Sighting> sightings = sightingDao.getTenMostRecentSightings();
            model.addAttribute("sightings", sightings);
        } catch (SuperSightingsPersistenceException ex) {
            model.addAttribute("error", ex);
        }
        
        return "homes";
    }
}
