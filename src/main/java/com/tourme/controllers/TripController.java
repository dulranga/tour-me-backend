package com.tourme.controllers;

import com.tourme.annotations.AuthenticatedUser;
import com.tourme.dto.ApiResponse;
import com.tourme.dto.TripDTO;
import com.tourme.services.AuthorizationService;
import com.tourme.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Admin endpoint to fetch all trips.
     */
    @GetMapping
    public ResponseEntity<?> getAllTrips(@AuthenticatedUser int authUserId) {
        try {
            authorizationService.validateUserRole(authUserId, "ADMINISTRATOR");
            List<TripDTO> trips = tripService.getAllTrips();
            return ApiResponse.ok("All trips retrieved successfully", trips);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve trips: " + e.getMessage());
        }
    }

    /**
     * Fetch driver's upcoming trips.
     */
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<?> getDriverTrips(@PathVariable int driverId, @AuthenticatedUser int authUserId) {
        try {
            // Ensure the driver is requesting their own trips or requester is admin
            authorizationService.validateUserAccess(driverId, authUserId);
            authorizationService.validateUserRole(driverId, "DRIVER");

            List<TripDTO> trips = tripService.getTripsByDriver(driverId);
            return ApiResponse.ok("Driver trips retrieved successfully", trips);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve driver trips: " + e.getMessage());
        }
    }

    /**
     * Fetch tourist's confirmed trips.
     */
    @GetMapping("/tourist/{userId}")
    public ResponseEntity<?> getTouristTrips(@PathVariable int userId, @AuthenticatedUser int authUserId) {
        try {
            // Ensure the tourist is requesting their own trips or requester is admin
            authorizationService.validateUserAccess(userId, authUserId);
            authorizationService.validateUserRole(userId, "TOURIST");

            List<TripDTO> trips = tripService.getTripsByTourist(userId);
            return ApiResponse.ok("Tourist trips retrieved successfully", trips);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve tourist trips: " + e.getMessage());
        }
    }
}
