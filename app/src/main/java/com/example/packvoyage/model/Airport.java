package com.example.packvoyage.model;

public class Airport {
    private String name;
    private Locality locality;

    public Airport(String name, Locality locality) {
        this.name = name;
        this.locality = locality;
    }

    public String getName() {
        return name;
    }

    public Locality getLocality() {
        return locality;
    }
}
