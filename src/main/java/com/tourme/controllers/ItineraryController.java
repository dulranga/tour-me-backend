package com.tourme.controllers;

import com.tourme.annotations.AuthenticatedUser;
import com.tourme.dto.ApiResponse;
import com.tourme.models.Itinerary;
import com.tourme.models.Tourist;
import com.tourme.models.User;
import com.tourme.repositories.ItineraryRepository;
import com.tourme.repositories.UserRepository;
import com.tourme.services.AuthorizationService;
import com.tourme.services.ItineraryService;
import com.tourme.services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * REST endpoint for managing itineraries.
 * Handles creation, retrieval, cancellation, and receipt management.
 */
@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {
    // Dependencies for data access and business logic
    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private AuthorizationService authorizationService;

    // Creates a new itinerary for the authenticated tourist
    @PostMapping
    public ResponseEntity<?> createItinerary(@AuthenticatedUser int touristId, @RequestBody Itinerary itinerary) {
        try {
            return itineraryService.createItinerary(touristId, itinerary);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to create itinerary: " + e.getMessage());
        }
    }

    // Returns all itineraries available for driver bidding
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableItineraries() {
        List<Itinerary> itineraries = itineraryService.getAvailableItineraries();
        return ApiResponse.ok("Available itineraries retrieved successfully", itineraries);
    }

    // Retrieves a single itinerary by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getItineraryDetails(@PathVariable int id) {
        return itineraryService.getItineraryById(id);
    }

    // Gets all itineraries created by a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserItineraries(@PathVariable int userId) {
        List<Itinerary> itineraries = itineraryService.getUserItineraries(userId);
        return ApiResponse.ok("User itineraries retrieved successfully", itineraries);
    }

    // Soft-deletes an itinerary (marks as CANCELLED, doesn't remove from DB)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelItinerary(@PathVariable int id, @AuthenticatedUser int touristId) {
        try {
            authorizationService.validateItineraryOwnership(id, touristId);
            return itineraryService.cancelItinerary(id);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to cancel itinerary: " + e.getMessage());
        }
    }

    // Uploads receipt file for an itinerary
    @PostMapping("/{itineraryId}/receipt")
    public ResponseEntity<?> uploadReceipt(@PathVariable int itineraryId,
            @RequestParam("file") MultipartFile file) {
        try {
            return receiptService.uploadReceipt(itineraryId, file);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to upload receipt: " + e.getMessage());
        }
    }

    // Retrieves receipt for an itinerary
    @GetMapping("/{itineraryId}/receipt")
    public ResponseEntity<?> getReceipt(@PathVariable int itineraryId) {
        try {
            return receiptService.getReceipt(itineraryId);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve receipt: " + e.getMessage());
        }
    }

    // Deletes receipt associated with an itinerary
    @DeleteMapping("/{itineraryId}/receipt")
    public ResponseEntity<?> deleteReceipt(@PathVariable int itineraryId) {
        try {
            return receiptService.deleteReceipt(itineraryId);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to delete receipt: " + e.getMessage());
        }
    }
}
