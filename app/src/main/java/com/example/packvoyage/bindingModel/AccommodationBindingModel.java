package com.example.packvoyage.bindingModel;


import java.util.ArrayList;

public class AccommodationBindingModel {
    private Integer id;
    private String name;
    private Integer localityId;
    private AccommodationTypeBindingModel accommodationType;
    private LocalityBindingModel locality;
    private ArrayList<ImageOrVideoBindingModel> pictureOrVideo;

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

    public Integer getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
    }

    public AccommodationTypeBindingModel getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(AccommodationTypeBindingModel accommodationType) {
        this.accommodationType = accommodationType;
    }

    public LocalityBindingModel getLocality() {
        return locality;
    }

    public void setLocality(LocalityBindingModel locality) {
        this.locality = locality;
    }

    public ArrayList<ImageOrVideoBindingModel> getPictureOrVideo() {
        return pictureOrVideo;
    }

    public void setPictureOrVideo(ArrayList<ImageOrVideoBindingModel> pictureOrVideo) {
        this.pictureOrVideo = pictureOrVideo;
    }
}
