package com.tourme.controllers;

import com.tourme.models.Itinerary;
import com.tourme.models.Tourist;
import com.tourme.models.User;
import com.tourme.repositories.ItineraryRepository;
import com.tourme.repositories.UserRepository;
import com.tourme.services.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
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

    /**
     * Create a new itinerary for a specific tourist
     * @param touristId - The ID of the tourist creating the itinerary. this is the foreign key which we use to link the itinerary to the tourist in the database.
     * @param itinerary - The itinerary details (pickup, destination, etc.) this is json file which we convert to object of itinerary class and then pass it to service.
    call the service to create a new itinerary for the specified tourist. 
    */
    @PostMapping
    public ResponseEntity<?> createItinerary(@RequestParam int touristId, @RequestBody Itinerary itinerary) {
        return itineraryService.createItinerary(touristId, itinerary);
    }

    /**
     * Get all itineraries available for bidding 
     * call the service to fetch all itineraries that are currently available for drivers to bid on.
     */
    @GetMapping("/available")
    public List<Itinerary> getAvailableItineraries() {
        return itineraryService.getAvailableItineraries();
    }

    /**
     * Get a specific itinerary's details by ID
     * @param id - The itinerary ID
     * call the service to fetch the details of a specific itinerary by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Itinerary> getItineraryDetails(@PathVariable int id) {
        return itineraryService.getItineraryById(id);
    }

    /**
     * Get all itineraries created by a specific tourist
     * @param userId - The tourist's user ID
     * call the service to fetch all itineraries created by a specific tourist using their user ID.
     */
    @GetMapping("/user/{userId}")
    public List<Itinerary> getUserItineraries(@PathVariable int userId) {
        return itineraryService.getUserItineraries(userId);
    }

    /**
     * Cancel an itinerary (soft delete - sets status to CANCELLED) does not delete the whole raw from the DB. 
     * @param id - The itinerary ID to cancel
     * call the service to cancel an itinerary by its ID. 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelItinerary(@PathVariable int id) {
        return itineraryService.cancelItinerary(id);
    }
}
