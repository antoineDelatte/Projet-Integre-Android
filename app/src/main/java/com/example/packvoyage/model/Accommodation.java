package com.example.packvoyage.model;

import java.util.ArrayList;

public class Accommodation {
    private String name;
    private Locality locality;
    private ArrayList<BedRoom> bedrooms;

    public Accommodation(String name, Locality locality, ArrayList<BedRoom> bedrooms) {
        this.name = name;
        this.locality = locality;
        this.bedrooms = bedrooms;
    }

    public String getName() {
        return name;
    }

    public Locality getLocality() {
        return locality;
    }

    public ArrayList<BedRoom> getBedrooms() {
        return bedrooms;
    }
}
