package com.example.packvoyage.model;

import java.util.ArrayList;

public class Flight {
    private int flightId;
    private int flightNumber;
    private boolean isGoing;
    private ArrayList<PlaneSeat>planeSeats;
    private Airport departureAirport;
    private Airport arrivalAirport;

    public Flight(){}

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

    public String getArrivalAndDestinationTitle(){
        return departureAirport.getLocality().getName() + " ("
                + departureAirport.getLocality().getCountryName() + ") - "
                + arrivalAirport.getLocality().getName() + " (" + arrivalAirport.getLocality().getCountryName() + ")";
    }
    public String getDepartureInfo(){
        return departureAirport.getName() + ", " + departureAirport.getLocality().getName();
        //return "Bruxelles Nord, Brussels, 6AM";
    }
    public String getArrivalInfo(){
        return arrivalAirport.getName() + ", " + arrivalAirport.getLocality().getName();
        //return "JFK Airport, New York, 3PM";
    }

    public static ArrayList<Flight>getOutwardsFlights(ArrayList<Flight>flights){
        ArrayList<Flight>outwardsFlights = new ArrayList<>();
        for(Flight flight : flights){
            if(flight.isGoing())
                outwardsFlights.add(flight);
        }
        return outwardsFlights;
    }

    public static ArrayList<Flight>getHomewardsFlights(ArrayList<Flight>flights){
        ArrayList<Flight>homewardsFlights = new ArrayList<>();
        for(Flight flight : flights){
            if(!flight.isGoing())
                homewardsFlights.add(flight);
        }
        return homewardsFlights;
    }
}
