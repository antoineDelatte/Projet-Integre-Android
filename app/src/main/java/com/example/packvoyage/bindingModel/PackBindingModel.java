package com.example.packvoyage.bindingModel;

import java.util.ArrayList;

public class PackBindingModel {

    private Integer id;
    private String name;
    private ArrayList<ImageOrVideoBindingModel> imageOrVideoBindingModels;

    public PackBindingModel(Integer id, String name, ArrayList<ImageOrVideoBindingModel> imageOrVideoBindingModels) {
        this.id = id;
        this.name = name;
        this.imageOrVideoBindingModels = imageOrVideoBindingModels;
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

    public ArrayList<ImageOrVideoBindingModel> getImageOrVideoBindingModels() {
        return imageOrVideoBindingModels;
    }

    public void setImageOrVideoBindingModels(ArrayList<ImageOrVideoBindingModel> imageOrVideoBindingModels) {
        this.imageOrVideoBindingModels = imageOrVideoBindingModels;
    }
}
