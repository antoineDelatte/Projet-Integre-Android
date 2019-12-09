package com.example.packvoyage.model;

public class Flight {
    private int flightId;
    private int flightNumber;
    private boolean isGoing;

    public Flight(int flightNumber, boolean isGoing) {
        this.flightNumber = flightNumber;
        this.isGoing = isGoing;
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

    public String getFullDescription(){
        return "-Company : Ryanair\n" +
                "\n-Departure info : Airport Charles Henry 15 january 2019 6AM, Belgium Brussels" +
                "\n-Arrival info : Airport Jean Claude 15 january 2019 3PM, United Stated New York" +
                "\n-Flight number : 627175656"; // todo
    }
}
