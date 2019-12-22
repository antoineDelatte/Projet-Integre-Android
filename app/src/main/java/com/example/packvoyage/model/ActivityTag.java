package com.example.packvoyage.model;

public class ActivityTag {
    private Integer id;
    private String name;

    public ActivityTag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ActivityTag(String name) {
        this(null, name);
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
