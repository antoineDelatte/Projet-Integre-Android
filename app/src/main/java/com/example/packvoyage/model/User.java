package com.example.packvoyage.model;

public class User {
    private int id;
    private String name;
    private String profile_pic_uri;

    public User() {}

    public User(int id, String name, String profile_pic_uri) {
        this.id = id;
        this.name = name;
        this.profile_pic_uri = profile_pic_uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_pic_uri() {
        return profile_pic_uri;
    }

    public void setProfile_pic_uri(String profile_pic_uri) {
        this.profile_pic_uri = profile_pic_uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
