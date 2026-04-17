package com.tourme.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receiptId;

    @OneToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    private String receiptUrl;
    private LocalDateTime uploadedAt;
    private String fileSize; // in bytes
    private String fileName;

    public Receipt() {
    }

    public Receipt(Itinerary itinerary, String receiptUrl, String fileName) {
        this.itinerary = itinerary;
        this.receiptUrl = receiptUrl;
        this.fileName = fileName;
        this.uploadedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
