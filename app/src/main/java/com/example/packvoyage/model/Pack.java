package com.example.packvoyage.model;

import java.util.ArrayList;

public class Pack {

    private Integer id;
    private String name;
    private String description;
    private String image_url;
    private ArrayList<Activity>activities;

    public Pack(Integer id, String name, String description, String image_url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
