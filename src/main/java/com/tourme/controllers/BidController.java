package com.tourme.controllers;

import com.tourme.annotations.AuthenticatedUser;
import com.tourme.dto.ApiResponse;
import com.tourme.dto.BidSubmitRequest;
import com.tourme.models.Bid;
import com.tourme.services.AuthorizationService;
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

    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Submit a new bid for an itinerary by a driver.
     * 
     * @param driverId   - The ID of the driver placing the bid, extracted from auth
     *                   token
     * @param bidRequest - The bid details containing itineraryId and bidAmount.
     *                   call the service to submit a new bid for the specified
     *                   itinerary by the specified driver.
     */
    @PostMapping
    public ResponseEntity<?> submitBid(@AuthenticatedUser int driverId,
            @RequestBody BidSubmitRequest bidRequest) {
        try {
            authorizationService.validateUserRole(driverId, "DRIVER");
            return bidService.submitBid(driverId, bidRequest);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to submit bid: " + e.getMessage());
        }
    }

    /**
     * Get all bids placed for a specific itinerary.
     * 
     * @param itineraryId - The ID of the itinerary
     *                    call the service to fetch all bids placed for a specific
     *                    itinerary using its ID.
     */
    @GetMapping("/itinerary/{itineraryId}")
    public ResponseEntity<?> getBidsForItinerary(@PathVariable int itineraryId, @AuthenticatedUser int authUserId) {
        try {
            authorizationService.validateItineraryBidReadAccess(itineraryId, authUserId);
            List<Bid> bids = bidService.getBidsForItinerary(itineraryId);
            return ApiResponse.ok("Bids retrieved successfully", bids);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve bids: " + e.getMessage());
        }
    }

    /**
     * Get bids submitted by a specific driver.
     * 
     * @param driverId - The ID of the driver
     *                 call the service to fetch all bids submitted by a specific
     *                 driver using their user ID.
     */
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<?> getBidsForDriver(@PathVariable int driverId, @AuthenticatedUser int authUserId) {
        try {
            authorizationService.validateUserAccess(driverId, authUserId);
            List<Bid> bids = bidService.getBidsForDriver(driverId);
            return ApiResponse.ok("Driver bids retrieved successfully", bids);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve driver bids: " + e.getMessage());
        }
    }

    /**
     * Get a single bid by ID.
     * 
     * @param id - The ID of the bid
     *           call the service to fetch the details of a specific bid by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBidById(@PathVariable int id, @AuthenticatedUser int authUserId) {
        try {
            authorizationService.validateBidReadAccess(id, authUserId);
            return bidService.getBidById(id);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve bid: " + e.getMessage());
        }
    }

    /**
     * Tourist selects a bid for their itinerary.
     * 
     * @param id        - The ID of the bid to select
     * @param touristId - The ID of the tourist selecting the bid.
     *                  call the service to select a bid for an itinerary by its ID
     *                  and the tourist's user ID.
     */
    @PostMapping("/{id}/select")
    public ResponseEntity<?> selectBid(@PathVariable int id, @AuthenticatedUser int touristId) {
        try {
            authorizationService.validateUserRole(touristId, "TOURIST");
            // selectBid in service performs internal ownership check via itineraryId
            return bidService.selectBid(id, touristId);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to select bid: " + e.getMessage());
        }
    }

    /**
     * Update an existing bid submitted by a driver.
     * Only PENDING bids can be updated.
     * 
     * @param id       - The ID of the bid to update
     * @param driverId - The ID of the driver (must be the bid owner)
     * @param bid      - Updated bid details (amount)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBid(@PathVariable int id, @AuthenticatedUser int driverId,
            @RequestBody com.tourme.models.Bid bid) {
        try {
            authorizationService.validateBidOwnership(id, driverId);
            return bidService.updateBid(id, driverId, bid);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to update bid: " + e.getMessage());
        }
    }
}
