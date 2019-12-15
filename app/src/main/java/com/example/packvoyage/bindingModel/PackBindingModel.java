package com.example.packvoyage.bindingModel;

import java.util.ArrayList;

public class PackBindingModel {

    private Integer id;
    private String name;
    private ArrayList<ImageOrVideoBindingModel> pictureOrVideo;

    public PackBindingModel(Integer id, String name, ArrayList<ImageOrVideoBindingModel> pictureOrVideo) {
        this.id = id;
        this.name = name;
        this.pictureOrVideo = pictureOrVideo;
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
}
