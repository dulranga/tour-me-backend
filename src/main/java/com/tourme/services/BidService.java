package com.tourme.services;

import com.tourme.dto.ApiResponse;
import com.tourme.dto.BidSubmitRequest;
import com.tourme.models.Bid;
import com.tourme.models.Driver;
import com.tourme.models.Itinerary;
import com.tourme.models.User;
import com.tourme.repositories.BidRepository;
import com.tourme.repositories.ItineraryRepository;
import com.tourme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    // Create a new bid
    public ResponseEntity<?> submitBid(int driverId, BidSubmitRequest bidRequest) {
        try {
            // Check if driver exists
            Optional<User> driverOpt = userRepository.findById(driverId);
            if (!driverOpt.isPresent() || !(driverOpt.get() instanceof Driver)) {
                return ApiResponse.notFound("Driver not found");
            }

            // Check if itinerary exists
            Optional<Itinerary> itineraryOpt = itineraryRepository.findById(bidRequest.getItineraryId());
            if (!itineraryOpt.isPresent()) {
                return ApiResponse.notFound("Itinerary not found");
            }

            // Create bid from request data
            Bid bid = new Bid();
            bid.setAmount(bidRequest.getBidAmount());
            bid.setDriver((Driver) driverOpt.get());
            bid.setItinerary(itineraryOpt.get());
            bid.setStatus("PENDING");

            // Save and return
            return ApiResponse.created("Bid submitted successfully", bidRepository.save(bid));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to submit bid: " + e.getMessage());
        }
    }

    // Get all bids for an itinerary
    public List<Bid> getBidsForItinerary(int itineraryId) {
        return bidRepository.findByItinerary_ItineraryId(itineraryId);
    }

    // Get all bids from a driver
    public List<Bid> getBidsForDriver(int driverId) {
        return bidRepository.findByDriver_UserId(driverId);
    }

    // Get a bid by ID
    public ResponseEntity<?> getBidById(int id) {
        Optional<Bid> bid = bidRepository.findById(id);
        if (bid.isPresent()) {
            return ApiResponse.ok("Bid retrieved successfully", bid.get());
        }
        return ApiResponse.notFound("Bid not found");
    }

    // Tourist selects a bid
    public ResponseEntity<?> selectBid(int id, int touristId) {
        try {
            // Check if bid exists
            Optional<Bid> bidOpt = bidRepository.findById(id);
            if (!bidOpt.isPresent()) {
                return ApiResponse.notFound("Bid not found");
            }

            // Check if tourist exists
            Optional<User> touristOpt = userRepository.findById(touristId);
            if (!touristOpt.isPresent()) {
                return ApiResponse.notFound("Tourist not found");
            }

            Bid bid = bidOpt.get();

            // Bid must be PENDING to be selected
            if (!bid.getStatus().equals("PENDING")) {
                return ApiResponse.badRequest("Bid is not available");
            }

            // Accept the bid
            bid.setStatus("ACCEPTED");
            Bid selectedBid = bidRepository.save(bid);
            return ApiResponse.ok("Bid selected successfully", selectedBid);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to select bid: " + e.getMessage());
        }
    }

    // Update an existing bid
    public ResponseEntity<?> updateBid(int id, int driverId, Bid updatedBid) {
        try {
            // Check if bid exists
            Optional<Bid> bidOpt = bidRepository.findById(id);
            if (!bidOpt.isPresent()) {
                return ApiResponse.notFound("Bid not found");
            }

            Bid bid = bidOpt.get();

            // Verify the driver owns this bid
            if (bid.getDriver().getUserId() != driverId) {
                return ApiResponse.forbidden("You are not authorized to update this bid");
            }

            // Only PENDING bids can be updated
            if (!bid.getStatus().equals("PENDING")) {
                return ApiResponse.badRequest("Only pending bids can be updated");
            }

            // Update bid amount
            bid.setAmount(updatedBid.getAmount());
            Bid updated = bidRepository.save(bid);
            return ApiResponse.ok("Bid updated successfully", updated);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to update bid: " + e.getMessage());
        }
    }
}