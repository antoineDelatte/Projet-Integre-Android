package com.example.packvoyage.bindingModel;

import java.util.ArrayList;

public class PackBindingModel {

    private Integer id;
    private String name;
    private ArrayList<ImageOrVideoBindingModel> pictureOrVideo;
    private ArrayList<TraductionBindingModel> traduction;
    private Double price;
    private ArrayList<ActivityBindingModel>activity;

    public PackBindingModel(){}

    public TraductionBindingModel getTraduction() {
        return traduction.get(0);
    }

    public void setTraduction(ArrayList<TraductionBindingModel> traduction) {
        this.traduction = traduction;
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

    public ArrayList<ImageOrVideoBindingModel> getPictureOrVideo() {
        return pictureOrVideo;
    }

    public void setPictureOrVideo(ArrayList<ImageOrVideoBindingModel> pictureOrVideo) {
        this.pictureOrVideo = pictureOrVideo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ArrayList<ActivityBindingModel> getActivity() {
        return activity;
    }

    public void setActivity(ArrayList<ActivityBindingModel> activity) {
        this.activity = activity;
    }
}
