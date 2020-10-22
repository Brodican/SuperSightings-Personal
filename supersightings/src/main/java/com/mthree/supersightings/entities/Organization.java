/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.supersightings.entities;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author utkua
 */
public class Organization {
    
    private int id;
    
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 80, message="Name must be equal to or less than 80 characters.")
    private String name;
    
    @NotBlank(message = "Description must not be empty.")
    private String description;
    
    @NotBlank(message = "Phone number must not be empty.")
    @Size(max = 20, message="Phone number must be equal to or less than 20 characters.")
    private String phoneNumber;
    
    @NotBlank(message = "Email must not be empty.")
    @Size(max = 50, message="Email must be equal to or less than 50 characters.")
    private String email;
    
    private List<Supe> supes;
    
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Supe> getSupes() {
        return supes;
    }

    public void setSupes(List<Supe> supes) {
        this.supes = supes;
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
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.description);
        hash = 37 * hash + Objects.hashCode(this.phoneNumber);
        hash = 37 * hash + Objects.hashCode(this.email);
        hash = 37 * hash + Objects.hashCode(this.supes);
        hash = 37 * hash + Objects.hashCode(this.address);
        hash = 37 * hash + Objects.hashCode(this.postCode);
        hash = 37 * hash + Objects.hashCode(this.city);
        hash = 37 * hash + Objects.hashCode(this.country);
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
        final Organization other = (Organization) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
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
        if (!Objects.equals(this.supes, other.supes)) {
            return false;
        }
        return true;
    }
    
}
