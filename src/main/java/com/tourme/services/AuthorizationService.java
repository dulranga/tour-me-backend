package com.tourme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourme.exceptions.ForbiddenException;
import com.tourme.models.Bid;
import com.tourme.models.Itinerary;
import com.tourme.repositories.BidRepository;
import com.tourme.repositories.ItineraryRepository;

/**
 * Authorization service for validating user permissions and ownership.
 * This service centralizes access control logic for various entities.
 * 
 * Methods throw ForbiddenException when a user lacks permission to perform an
 * action.
 */
@Service
public class AuthorizationService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    /**
     * Validates that a driver owns a specific bid.
     * 
     * @param bidId    The ID of the bid to check
     * @param driverId The ID of the driver
     * @throws ForbiddenException if the driver does not own the bid or bid doesn't
     *                            exist
     */
    public void validateBidOwnership(int bidId, int driverId) throws ForbiddenException {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ForbiddenException("Bid not found"));

        if (bid.getDriver() == null || bid.getDriver().getUserId() != driverId) {
            throw new ForbiddenException("You are not authorized to perform this action on this bid");
        }
    }

    /**
     * Validates that a tourist owns a specific itinerary.
     * 
     * @param itineraryId The ID of the itinerary to check
     * @param touristId   The ID of the tourist
     * @throws ForbiddenException if the tourist does not own the itinerary or
     *                            itinerary doesn't exist
     */
    public void validateItineraryOwnership(int itineraryId, int touristId) throws ForbiddenException {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new ForbiddenException("Itinerary not found"));

        if (itinerary.getTourist() == null || itinerary.getTourist().getUserId() != touristId) {
            throw new ForbiddenException("You are not authorized to perform this action on this itinerary");
        }
    }

    /**
     * Validates that a user can access another user's data.
     * Users can only access their own data (no delegation).
     * 
     * @param userId           The ID of the user whose data is being accessed
     * @param requestingUserId The ID of the user making the request
     * @throws ForbiddenException if the requesting user is not the owner
     */
    public void validateUserAccess(int userId, int requestingUserId) throws ForbiddenException {
        if (userId != requestingUserId) {
            throw new ForbiddenException("You are not authorized to access this user's data");
        }
    }
}
