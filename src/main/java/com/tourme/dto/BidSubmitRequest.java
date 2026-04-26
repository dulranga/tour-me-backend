package com.tourme.dto;

/**
 * DTO for submitting a new bid
 * Contains the itinerary ID and bid amount.
 * Driver ID is inferred from authentication context.
 */
public class BidSubmitRequest {
    public int itineraryId;
    public double bidAmount;

    public BidSubmitRequest() {
    }

    public BidSubmitRequest(int itineraryId, double bidAmount) {
        this.itineraryId = itineraryId;
        this.bidAmount = bidAmount;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(int itineraryId) {
        this.itineraryId = itineraryId;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }
}
