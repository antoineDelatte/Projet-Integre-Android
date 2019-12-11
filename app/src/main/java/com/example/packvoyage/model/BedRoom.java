package com.example.packvoyage.model;

public class BedRoom {
    private int id;
    private int nbBeds;
    private double price;

    public BedRoom(int id, int nbBeds, double price) {
        this.id = id;
        this.nbBeds = nbBeds;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getNbBeds() {
        return nbBeds;
    }

    public void setNbBeds(int nbBeds) {
        this.nbBeds = nbBeds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getShortDescription(){
        return nbBeds + " - " + price;
    }
}
