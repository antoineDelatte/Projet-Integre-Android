package com.example.packvoyage.model;

public class User {
    private String user_id;
    private String FirstName;
    private String profile_pic_uri;
    private String access_token;
    private String LastName;
    private String Email;
    private String Username;
    private Integer expires_in;

    public User() {}

    public User(String user_id, String FirstName, String LastName, String profile_pic_uri) {
        this.user_id = user_id;
        this.FirstName = FirstName;
        this.profile_pic_uri = profile_pic_uri;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String name) {
        this.FirstName = name;
    }

    public String getProfile_pic_uri() {
        return profile_pic_uri;
    }

    public void setProfile_pic_uri(String profile_pic_uri) {
        this.profile_pic_uri = profile_pic_uri;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
