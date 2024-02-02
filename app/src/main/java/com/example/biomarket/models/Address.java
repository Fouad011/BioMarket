package com.example.biomarket.models;

import java.util.Objects;

public class Address {
    public String country, city, zipCode, street;

    public Address() {
    }

    public Address(String country, String city, String street, String zipCode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public Boolean isEmpty(){
        if (this.getCountry()==null || this.getCity()==null || this.getZipCode()==null || this.getStreet()==null){
            return true;
        }
        return this.getCountry().isEmpty() || this.getCity().isEmpty() || this.getZipCode().isEmpty() || this.getStreet().isEmpty();
    }
}
