package com.tourme.repositories;

import com.tourme.models.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
    List<Itinerary> findByStatus(String status);
    List<Itinerary> findByTourist_UserId(int userId);
}
