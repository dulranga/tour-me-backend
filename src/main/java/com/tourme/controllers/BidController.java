package com.tourme.controllers;

import com.tourme.models.Bid;
import com.tourme.models.Driver;
import com.tourme.models.Itinerary;
import com.tourme.repositories.BidRepository;
import com.tourme.repositories.ItineraryRepository;
import com.tourme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> submitBid(@RequestParam int driverId, @RequestParam int itineraryId, @RequestBody Bid bid) {
        return null;
    }

    @GetMapping("/itinerary/{itineraryId}")
    public List<Bid> getBidsForItinerary(@PathVariable int itineraryId) {
        return null;
    }

    @PostMapping("/{id}/select")
    public ResponseEntity<?> selectBid(@PathVariable int id) {
        return null;
    }
}
