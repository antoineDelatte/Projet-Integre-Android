package com.example.packvoyage.model;

public class PlaneSeat {
    private int id;
    private String seatNumber;
    private double price;
    private boolean isBusinessClass;

    public PlaneSeat(){}

    public PlaneSeat(int id, String seatNumber, double price) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBusinessClass() {
        return isBusinessClass;
    }

    public void setBusinessClass(boolean businessClass) {
        isBusinessClass = businessClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return seatNumber + " " + price + " €";
    }
}
