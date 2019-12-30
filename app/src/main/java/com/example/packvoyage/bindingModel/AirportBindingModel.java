package com.example.packvoyage.bindingModel;

public class AirportBindingModel {
    private Integer id;
    private String name;
    private Integer localityId;
    private LocalityBindingModel locality;


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

    public LocalityBindingModel getLocality() {
        return locality;
    }

    public void setLocality(LocalityBindingModel locality) {
        this.locality = locality;
    }
}
