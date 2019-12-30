package com.example.packvoyage.bindingModel;

import java.util.ArrayList;

public class AccommodationOfPackBindingModel {
    private Integer accommodationId;
    private AccommodationBindingModel accommodation;

    public Integer getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Integer accommodationId) {
        this.accommodationId = accommodationId;
    }

    public AccommodationBindingModel getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(AccommodationBindingModel accommodation) {
        this.accommodation = accommodation;
    }
}
