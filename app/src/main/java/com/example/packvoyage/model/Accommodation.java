package com.example.packvoyage.model;

import com.example.packvoyage.R;

import java.util.ArrayList;

public class Accommodation {
    private String name;
    private Locality locality;
    private ArrayList<BedRoom> bedrooms;
    private String image_uri;
    private AccommodationType accommodationType;

    public Accommodation(){}

    public Accommodation(String name, Locality locality, ArrayList<BedRoom> bedrooms, String image_uri) {
        this.name = name;
        this.locality = locality;
        this.bedrooms = bedrooms;
        this.image_uri = image_uri;
    }

    public Accommodation(String name, Locality locality, ArrayList<BedRoom> bedrooms) {
        this(name, locality, bedrooms, null);
    }

    public Accommodation(String name, Locality locality, String image_uri) {
        this(name, locality, null, image_uri);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public ArrayList<BedRoom> getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(ArrayList<BedRoom> bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(AccommodationType accommodationType) {
        this.accommodationType = accommodationType;
    }
}
