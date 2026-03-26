package com.tourme.services;

import com.tourme.models.Itinerary;
import com.tourme.models.Tourist;
import com.tourme.models.User;
import com.tourme.repositories.ItineraryRepository;
import com.tourme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Make a new trip for a tourist.
     * Check if tourist exists, link the trip to them, set status to PENDING, save.
     */
    public ResponseEntity<?> createItinerary(int touristId, Itinerary itinerary) {
        Optional<User> userOptional = userRepository.findById(touristId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Tourist with ID " + touristId + " not found.");
        }

        User user = userOptional.get();
        if (!(user instanceof Tourist)) {
            return ResponseEntity.badRequest().body("Error: User with ID " + touristId + " is not a tourist.");
        }

        Tourist tourist = (Tourist) user;
        itinerary.setTourist(tourist);
        itinerary.setStatus("PENDING"); // Initial status before bidding starts

        Itinerary savedItinerary = itineraryRepository.save(itinerary);
        return ResponseEntity.ok(savedItinerary);
    }

    /**
     * Get all trips ready for drivers to bid on (status PENDING).
     */
    public List<Itinerary> getAvailableItineraries() {
        return itineraryRepository.findByStatus("PENDING");
    }

    /**
     * Get info for one trip by its ID.
     */
    public ResponseEntity<Itinerary> getItineraryById(int id) {
        Optional<Itinerary> itineraryOptional = itineraryRepository.findById(id);
        if (itineraryOptional.isPresent()) {
            return ResponseEntity.ok(itineraryOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all trips made by one user.
     */
    public List<Itinerary> getUserItineraries(int userId) {
        return itineraryRepository.findByTourist_UserId(userId);
    }

    /**
     * Cancel a trip by setting status to CANCELLED (keep in database).
     */
    public ResponseEntity<?> cancelItinerary(int id) {
        Optional<Itinerary> itineraryOptional = itineraryRepository.findById(id);
        if (itineraryOptional.isPresent()) {
            Itinerary itinerary = itineraryOptional.get();
            itinerary.setStatus("CANCELLED");
            itineraryRepository.save(itinerary);
            return ResponseEntity.ok("Itinerary has been successfully cancelled.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}