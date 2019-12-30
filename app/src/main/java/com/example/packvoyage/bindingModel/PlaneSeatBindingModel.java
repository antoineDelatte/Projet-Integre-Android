package com.example.packvoyage.bindingModel;

public class PlaneSeatBindingModel {
    private Integer id;
    private Boolean isBusinessClass;
    private String seatNumber;
    private Double price;
    private Integer reservationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getBusinessClass() {
        return isBusinessClass;
    }

    public void setBusinessClass(Boolean businessClass) {
        isBusinessClass = businessClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }
}
