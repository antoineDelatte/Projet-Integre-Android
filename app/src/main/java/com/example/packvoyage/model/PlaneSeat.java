package com.example.packvoyage.model;

public class PlaneSeat {
    private int id;
    private String seatNumber;
    private double price;

    public PlaneSeat(int id, String seatNumber, double price) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public int getId() {
        return id;
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
}
