package com.tourme.controllers;

import com.tourme.dto.ApiResponse;
import com.tourme.models.Itinerary;
import com.tourme.models.Tourist;
import com.tourme.models.User;
import com.tourme.repositories.ItineraryRepository;
import com.tourme.repositories.UserRepository;
import com.tourme.services.ItineraryService;
import com.tourme.services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private ReceiptService receiptService;

    /**
     * Create a new itinerary for a specific tourist
     * 
     * @param touristId - The ID of the tourist creating the itinerary. this is the
     *                  foreign key which we use to link the itinerary to the
     *                  tourist in the database.
     * @param itinerary - The itinerary details (pickup, destination, etc.) this is
     *                  json file which we convert to object of itinerary class and
     *                  then pass it to service.
     *                  call the service to create a new itinerary for the specified
     *                  tourist.
     */
    @PostMapping
    public ResponseEntity<?> createItinerary(@RequestParam int touristId, @RequestBody Itinerary itinerary) {
        try {
            return itineraryService.createItinerary(touristId, itinerary);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to create itinerary: " + e.getMessage());
        }
    }

    /**
     * Get all itineraries available for bidding
     * call the service to fetch all itineraries that are currently available for
     * drivers to bid on.
     */
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableItineraries() {
        List<Itinerary> itineraries = itineraryService.getAvailableItineraries();
        return ApiResponse.ok("Available itineraries retrieved successfully", itineraries);
    }

    /**
     * Get a specific itinerary's details by ID
     * 
     * @param id - The itinerary ID
     *           call the service to fetch the details of a specific itinerary by
     *           its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getItineraryDetails(@PathVariable int id) {
        return itineraryService.getItineraryById(id);
    }

    /**
     * Get all itineraries created by a specific tourist
     * 
     * @param userId - The tourist's user ID
     *               call the service to fetch all itineraries created by a specific
     *               tourist using their user ID.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserItineraries(@PathVariable int userId) {
        List<Itinerary> itineraries = itineraryService.getUserItineraries(userId);
        return ApiResponse.ok("User itineraries retrieved successfully", itineraries);
    }

    /**
     * Cancel an itinerary (soft delete - sets status to CANCELLED) does not delete
     * the whole raw from the DB.
     * 
     * @param id - The itinerary ID to cancel
     *           call the service to cancel an itinerary by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelItinerary(@PathVariable int id) {
        try {
            return itineraryService.cancelItinerary(id);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to cancel itinerary: " + e.getMessage());
        }
    }

    /**
     * Upload a receipt for an itinerary
     * 
     * @param itineraryId - The ID of the itinerary
     * @param file        - The receipt file to upload
     */
    @PostMapping("/{itineraryId}/receipt")
    public ResponseEntity<?> uploadReceipt(@PathVariable int itineraryId,
            @RequestParam("file") MultipartFile file) {
        try {
            return receiptService.uploadReceipt(itineraryId, file);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to upload receipt: " + e.getMessage());
        }
    }

    /**
     * Get receipt for an itinerary
     * 
     * @param itineraryId - The ID of the itinerary
     */
    @GetMapping("/{itineraryId}/receipt")
    public ResponseEntity<?> getReceipt(@PathVariable int itineraryId) {
        try {
            return receiptService.getReceipt(itineraryId);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve receipt: " + e.getMessage());
        }
    }

    /**
     * Delete receipt for an itinerary
     * 
     * @param itineraryId - The ID of the itinerary
     */
    @DeleteMapping("/{itineraryId}/receipt")
    public ResponseEntity<?> deleteReceipt(@PathVariable int itineraryId) {
        try {
            return receiptService.deleteReceipt(itineraryId);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to delete receipt: " + e.getMessage());
        }
    }
}
