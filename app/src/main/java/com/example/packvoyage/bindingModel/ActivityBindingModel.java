package com.example.packvoyage.bindingModel;

import java.util.ArrayList;

public class ActivityBindingModel {
    private Integer id;
    private Double price;
    private String name;
    private String streetName;
    private String streetNumber;
    private LocalityBindingModel locality;
    private ArrayList<ImageOrVideoBindingModel> pictureOrVideo;
    private ArrayList<ActivityTagBindingModel>tagOfActivity;

    public ActivityBindingModel(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public LocalityBindingModel getLocality() {
        return locality;
    }

    public void setLocality(LocalityBindingModel locality) {
        this.locality = locality;
    }

    public ArrayList<ImageOrVideoBindingModel> getPictureOrVideo() {
        return pictureOrVideo;
    }

    public void setPictureOrVideo(ArrayList<ImageOrVideoBindingModel> pictureOrVideo) {
        this.pictureOrVideo = pictureOrVideo;
    }

    public ArrayList<ActivityTagBindingModel> getTagOfActivity() {
        return tagOfActivity;
    }

    public void setTagOfActivity(ArrayList<ActivityTagBindingModel> tagOfActivity) {
        this.tagOfActivity = tagOfActivity;
    }
}
