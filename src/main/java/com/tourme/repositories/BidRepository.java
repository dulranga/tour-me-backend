package com.tourme.repositories;

import com.tourme.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
    List<Bid> findByItinerary_ItineraryId(int itineraryId);

    List<Bid> findByDriver_UserId(int userId);

    List<Bid> findByItinerary_Tourist_UserId(int userId);

    List<Bid> findByStatus(String status);

    List<Bid> findByStatusAndDriver_UserId(String status, int userId);

    List<Bid> findByStatusAndItinerary_Tourist_UserId(String status, int userId);
}
