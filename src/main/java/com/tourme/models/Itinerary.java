package com.tourme.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "itineraries")
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itineraryId;

    private String pickupLocation;
    private String destination;
    private String status; // "PENDING", "ACTIVE", "COMPLETED", "CANCELLED"

    @ManyToOne
    @JoinColumn(name = "tourist_id")
    private Tourist tourist;

    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL)
    private List<Bid> bids;

    public Itinerary() {}

    public Itinerary(String pickupLocation, String destination, Tourist tourist) {
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.tourist = tourist;
        this.status = "PENDING";
    }

    // --- Getters and Setters ---

    public int getItineraryId() {
        return itineraryId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Tourist getTourist() {
        return tourist;
    }

    public void setTourist(Tourist tourist) {
        this.tourist = tourist;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}
