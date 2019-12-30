package com.example.packvoyage.model;

import java.util.ArrayList;

public class Activity {
    private int id;
    private String name;
    private Double price;
    private String location;
    private String image_url;
    private ActivityTag tag;

    public Activity(){ }

    public Activity(int id, String name, double price, String location, String image_url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.location = location;
        this.image_url = image_url;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getShortDescription(){
        return name + " " + price + "€";
    }

    public ActivityTag getTag() {
        return tag;
    }

    public void setTag(ActivityTag tag) {
        this.tag = tag;
    }

    public static ArrayList<Activity> getPayingActivities(ArrayList<Activity>activities){
        ArrayList<Activity>payingActivities = new ArrayList<>();
        for(Activity act : activities){
            if(act.getPrice() != null && act.getPrice() > 0)
                payingActivities.add(act);
        }
        return payingActivities;
    }
}
