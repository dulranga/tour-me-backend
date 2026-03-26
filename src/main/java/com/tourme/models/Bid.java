package com.tourme.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bidId;

    private double amount;
    private String status; // "PENDING", "ACCEPTED", "DECLINED"

    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public Bid() {}

    public Bid(double amount, Itinerary itinerary, Driver driver) {
        this.amount = amount;
        this.itinerary = itinerary;
        this.driver = driver;
        this.status = "PENDING";
    }
 
    public int getBidId() {
        return bidId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
