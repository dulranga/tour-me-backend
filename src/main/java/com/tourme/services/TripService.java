package com.tourme.services;

import com.tourme.dto.TripDTO;
import com.tourme.repositories.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {

    @Autowired
    private BidRepository bidRepository;

    private static final String ACCEPTED_STATUS = "ACCEPTED";

    /**
     * Get all trips (accepted bids) for admin.
     */
    public List<TripDTO> getAllTrips() {
        return bidRepository.findByStatus(ACCEPTED_STATUS).stream()
                .map(TripDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Get all upcoming trips for a specific driver.
     */
    public List<TripDTO> getTripsByDriver(int driverId) {
        return bidRepository.findByStatusAndDriver_UserId(ACCEPTED_STATUS, driverId).stream()
                .map(TripDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Get all confirmed trips for a specific tourist.
     */
    public List<TripDTO> getTripsByTourist(int touristId) {
        return bidRepository.findByStatusAndItinerary_Tourist_UserId(ACCEPTED_STATUS, touristId).stream()
                .map(TripDTO::new)
                .collect(Collectors.toList());
    }
}
