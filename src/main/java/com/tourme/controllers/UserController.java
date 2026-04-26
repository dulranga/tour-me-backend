package com.tourme.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourme.annotations.AuthenticatedUser;
import com.tourme.dto.ApiResponse;
import com.tourme.dto.DriverRegisterRequest;
import com.tourme.dto.UserRegisterRequest;
import com.tourme.exceptions.UserNotFoundException;
import com.tourme.models.Administrator;
import com.tourme.models.Driver;
import com.tourme.models.Tourist;
import com.tourme.models.User;
import com.tourme.services.AuthorizationService;
import com.tourme.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Get all users in the system (Tourists, Drivers, and Administrators)
     * dcalls the relevent function in services to fetch all users.
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ApiResponse.ok("Users retrieved successfully", users);
    }

    /**
     * Get a specific user by ID
     * call the service to fetch a user by their ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User u = userService.getUserById(id);
        if (u != null) {
            return ApiResponse.ok("User retrieved successfully", u);
        } else {
            return ApiResponse.notFound("User not found");
        }
    }

    /**
     * Register a new Tourist
     * tourist first comes as .json object then we create a object of relevant
     * class and call the service to save the tourist in the database.
     */
    @PostMapping("/register/tourist")
    public ResponseEntity<?> registerTourist(@RequestBody UserRegisterRequest data) {
        Tourist t = new Tourist();
        t.setName(data.name);
        t.setEmail(data.email);
        t.setPasswordHash(data.password);
        Tourist registeredTourist = userService.registerTourist(t);
        return ApiResponse.ok("Tourist registered successfully", registeredTourist);
    }

    /**
     * Register a new Driver
     * driver first comes as .json object then we create a object of relevant
     * class and call the service to save the driver in the database.
     */
    @PostMapping("/register/driver")
    public ResponseEntity<?> registerDriver(@RequestBody DriverRegisterRequest data) {
        Driver d = new Driver();
        d.setName(data.name);
        d.setEmail(data.email);
        d.setPasswordHash(data.password);
        d.setLicenseNumber(data.licenseNumber);
        d.setVehicleDetails(data.vehicleDetails);
        Driver registeredDriver = userService.registerDriver(d);
        return ApiResponse.ok("Driver registered successfully", registeredDriver);
    }

    /**
     * Update a user's profile
     * call the service to update the user's profile.
     */
    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable int id, @AuthenticatedUser int authenticatedUserId,
            @RequestBody User profileData) {
        try {
            authorizationService.validateUserAccess(id, authenticatedUserId);
            User updatedUser = userService.updateProfile(id, profileData);
            return ApiResponse.ok("Profile updated successfully", updatedUser);
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound("User not found");
        } catch (Exception e) {
            return ApiResponse.internalServerError(e.getMessage());
        }
    }

    /**
     * Update a driver's vehicle details
     * call the service to update the driver's vehicle details.
     */
    @PutMapping("/{id}/vehicle")
    public ResponseEntity<?> updateVehicleDetails(@PathVariable int id, @AuthenticatedUser int authenticatedUserId,
            @RequestBody String vehicleDetails) {
        try {
            authorizationService.validateUserAccess(id, authenticatedUserId);
            Driver updatedDriver = userService.updateVehicleDetails(id, vehicleDetails);
            return ApiResponse.ok("Vehicle details updated successfully", updatedDriver);
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound("User not found");
        } catch (IllegalArgumentException e) {
            return ApiResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.internalServerError(e.getMessage());
        }
    }

    /**
     * Get all tourists in the system
     * call the service to fetch all tourists.
     */
    @GetMapping("/tourists")
    public ResponseEntity<?> getAllTourists() {
        List<User> tourists = userService.getAllTourists();
        return ApiResponse.ok("Tourists retrieved successfully", tourists);
    }

    /**
     * Get all drivers in the system
     * call the service to fetch all drivers.
     */
    @GetMapping("/drivers")
    public ResponseEntity<?> getAllDrivers() {
        List<User> drivers = userService.getAllDrivers();
        return ApiResponse.ok("Drivers retrieved successfully", drivers);
    }

    /**
     * Simple health check endpoint
     */
    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        return ApiResponse.ok("Service is healthy", "TourMe service is running");
    }
}
