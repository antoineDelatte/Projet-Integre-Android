package com.example.packvoyage.bindingModel;

public class PackBindingModel {

    private Integer id;
    private String name;
    private ImageOrVideo[] imageOrVideos;

    public PackBindingModel(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
