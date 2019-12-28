package com.example.packvoyage.model;

public class User {
    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String profile_pic_uri;

    public User() {}

    public User(int id, String userName, String profile_pic_uri) {
        this.id = id;
        this.userName = userName;
        this.profile_pic_uri = profile_pic_uri;
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
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
