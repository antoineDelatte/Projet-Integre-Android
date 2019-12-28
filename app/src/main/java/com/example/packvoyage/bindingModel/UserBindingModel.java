package com.example.packvoyage.bindingModel;

//used to send login and sign up information

public class UserBindingModel {
    private String FirstName;
    private String profile_pic_uri;
    private String LastName;
    private String Email;
    private String Username;
    private String Password;
    private String ConfirmPassword;

    public UserBindingModel(){}

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }
}
