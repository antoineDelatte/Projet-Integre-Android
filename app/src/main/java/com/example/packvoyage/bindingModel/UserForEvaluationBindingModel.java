package com.example.packvoyage.bindingModel;

import java.util.ArrayList;

public class UserForEvaluationBindingModel {
    private String lastName;
    private String firstName;
    private ArrayList<ImageOrVideoBindingModel> pictureOrVideo;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public ArrayList<ImageOrVideoBindingModel> getPictureOrVideo() {
        return pictureOrVideo;
    }

    public void setPictureOrVideo(ArrayList<ImageOrVideoBindingModel> pictureOrVideo) {
        this.pictureOrVideo = pictureOrVideo;
    }
}
