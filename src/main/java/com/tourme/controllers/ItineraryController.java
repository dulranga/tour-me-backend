package com.tourme.controllers;

import com.tourme.models.Itinerary;
import com.tourme.models.Tourist;
import com.tourme.models.User;
import com.tourme.repositories.ItineraryRepository;
import com.tourme.repositories.UserRepository;
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

    @PostMapping
    public ResponseEntity<?> createItinerary(@RequestParam int touristId, @RequestBody Itinerary itinerary) {
        return null;
    }

    @GetMapping("/available")
    public List<Itinerary> getAvailableItineraries() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Itinerary> getItineraryDetails(@PathVariable int id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelItinerary(@PathVariable int id) {
        return null;
    }
}
