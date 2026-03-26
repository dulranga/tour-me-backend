package com.tourme.controllers;

import com.tourme.models.Bid;
import com.tourme.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private BidService bidService;

    /**
     * Submit a new bid for an itinerary by a driver.
     * @param driverId - The ID of the driver placing the bid, used as a foreign key
     * @param itineraryId - The ID of the itinerary the bid is for, used as a foreign key
     * @param bid - The bid details. this is json file which we convert to object of bid class and then pass it to service.
     * call the service to submit a new bid for the specified itinerary by the specified driver. 
     */
    @PostMapping
    public ResponseEntity<?> submitBid(@RequestParam int driverId, @RequestParam int itineraryId, @RequestBody Bid bid) {
        return bidService.submitBid(driverId, itineraryId, bid);
    }

    /**
     * Get all bids placed for a specific itinerary.
     * @param itineraryId - The ID of the itinerary
     * call the service to fetch all bids placed for a specific itinerary using its ID.
     */
    @GetMapping("/itinerary/{itineraryId}")
    public List<Bid> getBidsForItinerary(@PathVariable int itineraryId) {
        return bidService.getBidsForItinerary(itineraryId);
    }

    /**
     * Get bids submitted by a specific driver.
     * @param driverId - The ID of the driver
     * call the service to fetch all bids submitted by a specific driver using their user ID.
     */
    @GetMapping("/driver/{driverId}")
    public List<Bid> getBidsForDriver(@PathVariable int driverId) {
        return bidService.getBidsForDriver(driverId);
    }

    /**
     * Get a single bid by ID.
     * @param id - The ID of the bid
     * call the service to fetch the details of a specific bid by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable int id) {
        return bidService.getBidById(id);
    }

    /**
     * Tourist selects a bid for their itinerary.
     * @param id - The ID of the bid to select
     * @param touristId - The ID of the tourist selecting the bid.
     * call the service to select a bid for an itinerary by its ID and the tourist's user ID.
     */
    @PostMapping("/{id}/select")
    public ResponseEntity<?> selectBid(@PathVariable int id, @RequestParam int touristId) {
        return bidService.selectBid(id, touristId);
    }
}
