/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.entities;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.Digits;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author utkua
 */
public class Location {
    
    private int id;
    
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 80, message="Name must be equal to or less than 80 characters.")
    private String name;
    
    @NotBlank(message = "Description must not be empty.")
    private String description;
    
    //@NotBlank(message = "Latitude must not be empty.")
    @Digits(integer=2, fraction=4, message="Latitude must have at most 2 digits to the left, and 4 digits to the right of the decimal point.")
    private BigDecimal latitude;
    
    //@NotBlank(message = "Longitude must not be empty.")
    @Digits(integer=3, fraction=4, message="Longitude must have at most 3 digits to the left, and 4 digits to the right of the decimal point.")
    private BigDecimal longitude;
    
    @NotBlank(message = "Address must not be empty.")
    @Size(max = 60, message="Address must be equal to or less than 60 characters.")
    private String address;
    
    @NotBlank(message = "Postcode must not be empty.")
    @Size(max = 10, message="Postcode must be equal to or less than 10 characters.")
    private String postCode;
    
    @NotBlank(message = "City must not be empty.")
    @Size(max = 70, message="City must be equal to or less than 70 characters.")
    private String city;
    
    @NotBlank(message = "Country must not be empty.")
    @Size(max = 70, message="Country must be equal to or less than 70 characters.")
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.latitude);
        hash = 41 * hash + Objects.hashCode(this.longitude);
        hash = 41 * hash + Objects.hashCode(this.address);
        hash = 41 * hash + Objects.hashCode(this.postCode);
        hash = 41 * hash + Objects.hashCode(this.city);
        hash = 41 * hash + Objects.hashCode(this.country);
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
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.postCode, other.postCode)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }
    
    
}
