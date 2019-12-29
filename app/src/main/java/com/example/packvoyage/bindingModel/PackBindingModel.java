package com.example.packvoyage.bindingModel;

import java.util.ArrayList;

public class PackBindingModel {

    private Integer id;
    private String name;
    private ArrayList<ImageOrVideoBindingModel> pictureOrVideo;
    private ArrayList<TraductionBindingModel> traduction;
    private Double price;

    public PackBindingModel(Integer id, String name, ArrayList<ImageOrVideoBindingModel> pictureOrVideo) {
        this.id = id;
        this.name = name;
        this.pictureOrVideo = pictureOrVideo;
    }

    public PackBindingModel(Integer id, ArrayList<TraductionBindingModel> traduction, String name) {
        this.id = id;
        this.name = name;
        this.traduction = traduction;
    }

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
}
