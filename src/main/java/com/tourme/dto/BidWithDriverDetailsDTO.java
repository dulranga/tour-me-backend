package com.tourme.dto;

import com.tourme.models.Bid;
import com.tourme.models.Driver;

/**
 * Data Transfer Object for bid information with driver contact details.
 * Used when tourists view bids for their itineraries.
 */
public class BidWithDriverDetailsDTO {
    private int bidId;
    private double amount;
    private String status;
    private int itineraryId;

    // Driver Contact Details
    private int driverId;
    private String driverName;
    private String driverEmail;
    private String vehicleDetails;

    public BidWithDriverDetailsDTO() {
    }

    public BidWithDriverDetailsDTO(Bid bid) {
        this.bidId = bid.getBidId();
        this.amount = bid.getAmount();
        this.status = bid.getStatus();

        if (bid.getItinerary() != null) {
            this.itineraryId = bid.getItinerary().getItineraryId();
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
    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
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

    public int getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(int itineraryId) {
        this.itineraryId = itineraryId;
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
