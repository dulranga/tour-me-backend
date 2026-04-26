package com.tourme.dto;

import com.tourme.models.Bid;
import com.tourme.models.Driver;
import com.tourme.models.Itinerary;

/**
 * Data Transfer Object representing a confirmed trip.
 * Aggregates information from Itinerary, Driver, and accepted Bid.
 */
public class TripDTO {
    private int tripId; // Using Bid ID as Trip ID
    private int itineraryId;
    private String pickupLocation;
    private String destination;
    private String status;
    private double agreedPrice;

    // Driver Details
    private int driverId;
    private String driverName;
    private String driverEmail;
    private String vehicleDetails;

    public TripDTO() {
    }

    public TripDTO(Bid bid) {
        this.tripId = bid.getBidId();
        this.agreedPrice = bid.getAmount();

        Itinerary itinerary = bid.getItinerary();
        if (itinerary != null) {
            this.itineraryId = itinerary.getItineraryId();
            this.pickupLocation = itinerary.getPickupLocation();
            this.destination = itinerary.getDestination();
            this.status = itinerary.getStatus();
        }

        Driver driver = bid.getDriver();
        if (driver != null) {
            this.driverId = driver.getUserId();
            this.driverName = driver.getName();
            this.driverEmail = driver.getEmail();
            this.vehicleDetails = driver.getVehicleDetails();
        }
    }

    // Getters and Setters
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(int itineraryId) {
        this.itineraryId = itineraryId;
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

    public double getAgreedPrice() {
        return agreedPrice;
    }

    public void setAgreedPrice(double agreedPrice) {
        this.agreedPrice = agreedPrice;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(String vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }
}
