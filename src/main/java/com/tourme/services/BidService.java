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
// Simple service for bid actions.
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    // Driver makes a new bid
    public ResponseEntity<?> submitBid(int driverId, BidSubmitRequest bidRequest) {
        try {
            // Find the driver
            Optional<User> driverOpt = userRepository.findById(driverId);
            if (!driverOpt.isPresent() || !(driverOpt.get() instanceof Driver)) {
                return ApiResponse.notFound("Driver not found");
            }

            // Find the trip
            Optional<Itinerary> itineraryOpt = itineraryRepository.findById(bidRequest.getItineraryId());
            if (!itineraryOpt.isPresent()) {
                return ApiResponse.notFound("Itinerary not found");
            }

            Itinerary itinerary = itineraryOpt.get();

            // Only open trips can get bids
            if (!"OPEN".equals(itinerary.getStatus())) {
                return ApiResponse.badRequest("Bidding is closed for this itinerary");
            }

            // Build the bid
            Bid bid = new Bid();
            bid.setAmount(bidRequest.getBidAmount());
            bid.setDriver((Driver) driverOpt.get());
            bid.setItinerary(itinerary);
            bid.setStatus("PENDING");

            // Save bid and send back result
            return ApiResponse.created("Bid submitted successfully", bidRepository.save(bid));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to submit bid: " + e.getMessage());
        }
    }

    // Get all bids for one trip
    public List<Bid> getBidsForItinerary(int itineraryId) {
        return bidRepository.findByItinerary_ItineraryId(itineraryId);
    }

    // Get all bids by one driver
    public List<Bid> getBidsForDriver(int driverId) {
        return bidRepository.findByDriver_UserId(driverId);
    }

    // Get all bids for a tourist's trips
    public List<Bid> getBidsForTourist(int touristId) {
        return bidRepository.findByItinerary_Tourist_UserId(touristId);
    }

    // Get one bid by its ID
    public ResponseEntity<?> getBidById(int id) {
        Optional<Bid> bid = bidRepository.findById(id);
        if (bid.isPresent()) {
            return ApiResponse.ok("Bid retrieved successfully", bid.get());
        }
        return ApiResponse.notFound("Bid not found");
    }

    // Tourist picks a bid
    public ResponseEntity<?> selectBid(int id, int touristId) {
        try {
            // Find the bid
            Optional<Bid> bidOpt = bidRepository.findById(id);
            if (!bidOpt.isPresent()) {
                return ApiResponse.notFound("Bid not found");
            }

            Bid bid = bidOpt.get();
            Itinerary itinerary = bid.getItinerary();

            // Only the trip owner can choose a bid
            if (itinerary == null || itinerary.getTourist() == null ||
                    itinerary.getTourist().getUserId() != touristId) {
                return ApiResponse.forbidden("You are not the owner of this itinerary");
            }

            // Only pending bids can be chosen
            if (!bid.getStatus().equals("PENDING")) {
                return ApiResponse.badRequest("Bid is not available");
            }

            // Trip must still be open
            if (!itinerary.getStatus().equals("OPEN")) {
                return ApiResponse.badRequest("Itinerary is not open for selection");
            }

            // Mark bid accepted and confirm trip
            bid.setStatus("ACCEPTED");
            itinerary.setStatus("CONFIRMED");
            itineraryRepository.save(itinerary);

            Bid selectedBid = bidRepository.save(bid);
            return ApiResponse.ok("Bid selected successfully", selectedBid);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to select bid: " + e.getMessage());
        }
    }

    // Change a bid's amount
    public ResponseEntity<?> updateBid(int id, int driverId, Bid updatedBid) {
        try {
            // Find the bid
            Optional<Bid> bidOpt = bidRepository.findById(id);
            if (!bidOpt.isPresent()) {
                return ApiResponse.notFound("Bid not found");
            }

            Bid bid = bidOpt.get();

            // Make sure the driver owns it
            if (bid.getDriver().getUserId() != driverId) {
                return ApiResponse.forbidden("You are not authorized to update this bid");
            }

            // Only pending bids can change
            if (!bid.getStatus().equals("PENDING")) {
                return ApiResponse.badRequest("Only pending bids can be updated");
            }

            // Save new amount
            bid.setAmount(updatedBid.getAmount());
            Bid updated = bidRepository.save(bid);
            return ApiResponse.ok("Bid updated successfully", updated);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to update bid: " + e.getMessage());
        }
    }
}