package com.example.packvoyage.model;

import java.util.ArrayList;

public class Flight {
    private int flightId;
    private int flightNumber;
    private boolean isGoing;
    private ArrayList<PlaneSeat>planeSeats;
    private Airport departureAirport;
    private Airport arrivalAirport;

    public Flight(int flightNumber, boolean isGoing) {
        this.flightNumber = flightNumber;
        this.isGoing = isGoing;
    }

    public ArrayList<PlaneSeat> getPlaneSeats() {
        return planeSeats;
    }

    public void setPlaneSeats(ArrayList<PlaneSeat> planeSeats) {
        this.planeSeats = planeSeats;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public boolean isGoing() {
        return isGoing;
    }

    public void setGoing(boolean going) {
        isGoing = going;
    }

    public String getShortDescription(){
        return departureAirport.getLocality().getName() + " - " + arrivalAirport.getLocality().getName();
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getFullDescription(){
        return "-Company : Ryanair\n" +
                "\n-Departure info : Airport Charles Henry 15 january 2019 6AM, Belgium Brussels" +
                "\n-Arrival info : Airport Jean Claude 15 january 2019 3PM, United Stated New York" +
                "\n-Flight number : 627175656"; // todo
    }
}
