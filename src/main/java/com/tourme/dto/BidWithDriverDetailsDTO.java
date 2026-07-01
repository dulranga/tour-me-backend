package com.tourme.dto;

import com.tourme.models.Bid;
import com.tourme.models.Driver;
import com.tourme.models.Itinerary;
import com.tourme.models.Tourist;

/**
 * Data Transfer Object for bid information with driver, itinerary, and tourist details.
 */
public class BidWithDriverDetailsDTO {
    private int bidId;
    private double amount;
    private String status;
    private int itineraryId;
    private String pickupLocation;
    private String destination;
    private String itineraryStatus;
    private int touristId;
    private String touristName;
    private String touristEmail;

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

        Itinerary itinerary = bid.getItinerary();
        if (itinerary != null) {
            this.itineraryId = itinerary.getItineraryId();
            this.pickupLocation = itinerary.getPickupLocation();
            this.destination = itinerary.getDestination();
            this.itineraryStatus = itinerary.getStatus();

            Tourist tourist = itinerary.getTourist();
            if (tourist != null) {
                this.touristId = tourist.getUserId();
                this.touristName = tourist.getName();
                this.touristEmail = tourist.getEmail();
            }
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

    public String getItineraryStatus() {
        return itineraryStatus;
    }

    public void setItineraryStatus(String itineraryStatus) {
        this.itineraryStatus = itineraryStatus;
    }

    public int getTouristId() {
        return touristId;
    }

    public void setTouristId(int touristId) {
        this.touristId = touristId;
    }

    public String getTouristName() {
        return touristName;
    }

    public void setTouristName(String touristName) {
        this.touristName = touristName;
    }

    public String getTouristEmail() {
        return touristEmail;
    }

    public void setTouristEmail(String touristEmail) {
        this.touristEmail = touristEmail;
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
