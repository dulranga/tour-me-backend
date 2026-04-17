package com.tourme.services;

import com.tourme.dto.ApiResponse;
import com.tourme.models.Itinerary;
import com.tourme.models.Receipt;
import com.tourme.repositories.ItineraryRepository;
import com.tourme.repositories.ReceiptRepository;
import com.tourme.services.storage.LocalFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private LocalFileStorageService fileStorageService;

    private static final String RECEIPT_DIR = "receipts";

    /**
     * Upload a receipt for an itinerary
     */
    public ResponseEntity<?> uploadReceipt(int itineraryId, MultipartFile file) {
        try {
            // Check if itinerary exists
            Optional<Itinerary> itineraryOpt = itineraryRepository.findById(itineraryId);
            if (!itineraryOpt.isPresent()) {
                return ApiResponse.notFound("Itinerary not found");
            }

            // Validate file
            if (file == null || file.isEmpty()) {
                return ApiResponse.badRequest("File is required");
            }

            // Check file size (max 10MB)
            long maxFileSize = 10 * 1024 * 1024; // 10MB
            if (file.getSize() > maxFileSize) {
                return ApiResponse.badRequest("File size exceeds maximum limit of 10MB");
            }

            // Delete existing receipt if any
            Optional<Receipt> existingReceipt = receiptRepository.findByItineraryItineraryId(itineraryId);
            if (existingReceipt.isPresent()) {
                Receipt oldReceipt = existingReceipt.get();
                try {
                    fileStorageService.deleteFile(oldReceipt.getReceiptUrl());
                } catch (Exception e) {
                    // Log but continue - file might already be deleted
                }
                receiptRepository.delete(oldReceipt);
            }

            // Upload file
            String receiptUrl = fileStorageService.uploadFile(file, RECEIPT_DIR);

            // Create and save receipt record
            Receipt receipt = new Receipt(itineraryOpt.get(), receiptUrl, file.getOriginalFilename());
            receipt.setFileSize(String.valueOf(file.getSize()));
            Receipt savedReceipt = receiptRepository.save(receipt);

            return ApiResponse.created("Receipt uploaded successfully", savedReceipt);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to upload receipt: " + e.getMessage());
        }
    }

    /**
     * Get receipt for an itinerary
     */
    public ResponseEntity<?> getReceipt(int itineraryId) {
        try {
            // Check if itinerary exists
            if (!itineraryRepository.findById(itineraryId).isPresent()) {
                return ApiResponse.notFound("Itinerary not found");
            }

            Optional<Receipt> receipt = receiptRepository.findByItineraryItineraryId(itineraryId);
            if (receipt.isPresent()) {
                return ApiResponse.ok("Receipt retrieved successfully", receipt.get());
            } else {
                return ApiResponse.notFound("No receipt found for this itinerary");
            }
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve receipt: " + e.getMessage());
        }
    }

    /**
     * Delete receipt for an itinerary
     */
    public ResponseEntity<?> deleteReceipt(int itineraryId) {
        try {
            Optional<Receipt> receipt = receiptRepository.findByItineraryItineraryId(itineraryId);
            if (!receipt.isPresent()) {
                return ApiResponse.notFound("Receipt not found");
            }

            Receipt receiptToDelete = receipt.get();
            try {
                fileStorageService.deleteFile(receiptToDelete.getReceiptUrl());
            } catch (Exception e) {
                // Log but continue
            }
            receiptRepository.delete(receiptToDelete);

            return ApiResponse.ok("Receipt deleted successfully", null);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to delete receipt: " + e.getMessage());
        }
    }
}
