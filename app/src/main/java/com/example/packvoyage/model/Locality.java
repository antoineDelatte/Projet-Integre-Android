package com.example.packvoyage.model;

public class Locality {
    private Integer id;
    private String name;
    private String zipCode;
    private String countryName;

    public Locality(String name) {
        this.name = name;
    }

    public Locality(Integer id, String name, String zipCode, String countryName) {
        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
        this.countryName = countryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String toString(){
        return name + " - " + countryName;
    }
}
