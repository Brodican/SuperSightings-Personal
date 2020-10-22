/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author utkua
 */
public class Sighting {
    
    private int id;
    
    private LocalDateTime sightingDate;
    
    private Location location;
    private List<Supe> supes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(LocalDateTime sightingDate) {
        this.sightingDate = sightingDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Supe> getSupes() {
        return supes;
    }

    public void setSupes(List<Supe> supes) {
        this.supes = supes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.sightingDate);
        hash = 29 * hash + Objects.hashCode(this.location);
        hash = 29 * hash + Objects.hashCode(this.supes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.sightingDate, other.sightingDate)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.supes, other.supes)) {
            return false;
        }
        return true;
    }
    
}
