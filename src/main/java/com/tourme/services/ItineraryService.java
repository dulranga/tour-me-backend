package com.tourme.services;

import com.tourme.dto.ApiResponse;
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
            return ApiResponse.badRequest("Error: Tourist with ID " + touristId + " not found.");
        }

        User user = userOptional.get();
        if (!(user instanceof Tourist)) {
            return ApiResponse.badRequest("Error: User with ID " + touristId + " is not a tourist.");
        }

        Tourist tourist = (Tourist) user;
        itinerary.setTourist(tourist);
        itinerary.setStatus("OPEN"); // Initial status before bidding starts

        Itinerary savedItinerary = itineraryRepository.save(itinerary);
        return ApiResponse.created("Itinerary created successfully", savedItinerary);
    }

    /**
     * Get all trips ready for drivers to bid on (status OPEN).
     */
    public List<Itinerary> getAvailableItineraries() {
        return itineraryRepository.findByStatus("OPEN");
    }

    /**
     * Get info for one trip by its ID.
     */
    public ResponseEntity<?> getItineraryById(int id) {
        Optional<Itinerary> itineraryOptional = itineraryRepository.findById(id);
        if (itineraryOptional.isPresent()) {
            return ApiResponse.ok("Itinerary retrieved successfully", itineraryOptional.get());
        } else {
            return ApiResponse.notFound("Itinerary not found");
        }
    }

    /**
     * Get all trips made by one user.
     */
    public List<Itinerary> getUserItineraries(int userId) {
        return itineraryRepository.findByTourist_UserId(userId);
    }

    /**
     * Cancel a trip (status remains CONFIRMED or OPEN until ended).
     * 
     * @deprecated Statuses are now restricted to DRAFT, OPEN, CONFIRMED.
     */
    public ResponseEntity<?> cancelItinerary(int id) {
        Optional<Itinerary> itineraryOptional = itineraryRepository.findById(id);
        if (itineraryOptional.isPresent()) {
            Itinerary itinerary = itineraryOptional.get();
            itinerary.setStatus("DRAFT"); // Reverting to draft instead of CANCELLED
            Itinerary cancelledItinerary = itineraryRepository.save(itinerary);
            return ApiResponse.ok("Itinerary moved to DRAFT", cancelledItinerary);
        } else {
            return ApiResponse.notFound("Itinerary not found");
        }
    }
}