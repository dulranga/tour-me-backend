package com.tourme.services;

import com.tourme.models.Bid;
import com.tourme.models.Driver;
import com.tourme.models.Itinerary;
import com.tourme.models.User;
import com.tourme.repositories.BidRepository;
import com.tourme.repositories.ItineraryRepository;
import com.tourme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> submitBid(int driverId, int itineraryId, Bid bid) {
        try {
            // Check if driver exists
            Optional<User> driverOpt = userRepository.findById(driverId);
            if (!driverOpt.isPresent() || !(driverOpt.get() instanceof Driver)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Driver not found");
            }

            // Check if itinerary exists
            Optional<Itinerary> itineraryOpt = itineraryRepository.findById(itineraryId);
            if (!itineraryOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Itinerary not found");
            }

            // Set bid details
            bid.setDriver((Driver) driverOpt.get());
            bid.setItinerary(itineraryOpt.get());
            bid.setStatus("PENDING");

            // Save and return
            return ResponseEntity.status(HttpStatus.CREATED).body(bidRepository.save(bid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
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
    public ResponseEntity<Bid> getBidById(int id) {
        Optional<Bid> bid = bidRepository.findById(id);
        return bid.isPresent() ? ResponseEntity.ok(bid.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Tourist selects a bid
    public ResponseEntity<?> selectBid(int id, int touristId) {
        try {
            // Check if bid exists
            Optional<Bid> bidOpt = bidRepository.findById(id);
            if (!bidOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid not found");
            }

            // Check if tourist exists
            Optional<User> touristOpt = userRepository.findById(touristId);
            if (!touristOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tourist not found");
            }

            Bid bid = bidOpt.get();

            // Bid must be PENDING to be selected
            if (!bid.getStatus().equals("PENDING")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bid is not available");
            }

            // Accept the bid
            bid.setStatus("ACCEPTED");
            bidRepository.save(bid);
            return ResponseEntity.ok("Bid selected");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}