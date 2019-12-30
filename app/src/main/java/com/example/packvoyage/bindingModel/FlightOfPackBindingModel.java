package com.example.packvoyage.bindingModel;

public class FlightOfPackBindingModel {
    private Integer packId;
    private Integer flightId;
    private Boolean isGoing;
    private FlightBindingModel flight;

    public FlightOfPackBindingModel(){}

    public Integer getPackId() {
        return packId;
    }

    public void setPackId(Integer packId) {
        this.packId = packId;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public Boolean getGoing() {
        return isGoing;
    }

    public void setGoing(Boolean going) {
        isGoing = going;
    }

    public FlightBindingModel getFlight() {
        return flight;
    }

    public void setFlight(FlightBindingModel flight) {
        this.flight = flight;
    }
}
