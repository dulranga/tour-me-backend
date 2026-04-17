package com.tourme.repositories;

import com.tourme.models.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

    /**
     * Find receipt by itinerary ID
     */
    Optional<Receipt> findByItineraryItineraryId(int itineraryId);
}
