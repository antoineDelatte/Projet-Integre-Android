package com.example.packvoyage.bindingModel;

public class FlightBindingModel {
    private Integer id;
    private Integer flightNumber;
    private Integer departureAirport;
    private Integer destinationAirport;
    private String companyName;
    private AirportBindingModel departureAirportNavigation;
    private AirportBindingModel destinationAirportNavigation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Integer getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Integer departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Integer getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Integer destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public AirportBindingModel getDepartureAirportNavigation() {
        return departureAirportNavigation;
    }

    public void setDepartureAirportNavigation(AirportBindingModel departureAirportNavigation) {
        this.departureAirportNavigation = departureAirportNavigation;
    }

    public AirportBindingModel getDestinationAirportNavigation() {
        return destinationAirportNavigation;
    }

    public void setDestinationAirportNavigation(AirportBindingModel destinationAirportNavigation) {
        this.destinationAirportNavigation = destinationAirportNavigation;
    }
}
