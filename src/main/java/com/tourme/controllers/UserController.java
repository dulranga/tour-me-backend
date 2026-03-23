package com.tourme.controllers;

import com.tourme.models.Administrator;
import com.tourme.models.Driver;
import com.tourme.models.Tourist;
import com.tourme.models.User;
import com.tourme.repositories.UserRepository;
import com.tourme.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     Get all users in the system (Tourists, Drivers, and Administrators)
     dcalls the relevent function in services to fetch all users.
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
    Get a specific user by ID
    call the service to fetch a user by their ID. 
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * Register a new Tourist
     * tourist first comes as .json object then we create a object of relevant class and
     * call the service to save the tourist in the database.
     */
    @PostMapping("/register/tourist")
    public Tourist registerTourist(@RequestBody Tourist tourist) {
        return userService.registerTourist(tourist);
    }

    /**
     * Register a new Driver
     * driver first comes as .json object then we create a object of relevant class and
     * call the service to save the driver in the database.
     */
    @PostMapping("/register/driver")
    public Driver registerDriver(@RequestBody Driver driver) {
        return userService.registerDriver(driver);
    }

    /**
     * Register a new Administrator
     * admin first comes as .json object then we create a object of relevant class and
     * call the service to save the admin in the database.
     */
    @PostMapping("/register/admin")
    public Administrator registerAdmin(@RequestBody Administrator admin) {
        return userService.registerAdmin(admin);
    }

    /**
     * Update a user's profile
     * call the service to update the user's profile.
     */
    @PutMapping("/{id}/profile")
    public ResponseEntity<User> updateProfile(@PathVariable int id, @RequestBody User profileData) {
        return userService.updateProfile(id, profileData);
    }

    /**
     * Update a driver's vehicle details
     * call the service to update the driver's vehicle details.
     */
    @PutMapping("/{id}/vehicle")
    public ResponseEntity<?> updateVehicleDetails(@PathVariable int id, @RequestBody String vehicleDetails) {
        return userService.updateVehicleDetails(id, vehicleDetails);
    }

    /**
     * Get all tourists in the system
     * call the service to fetch all tourists.
     */
    @GetMapping("/tourists")
    public List<User> getAllTourists() {
        return userService.getAllTourists();
    }

    /**
     * Get all drivers in the system
     * call the service to fetch all drivers.
     */
    @GetMapping("/drivers")
    public List<User> getAllDrivers() {
        return userService.getAllDrivers();
    }

    /**
     * Simple health check endpoint
     */
    @GetMapping("/status")
    public String getStatus() {
        return "TourMe service is running";
    }
}
