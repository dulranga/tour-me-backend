package com.tourme.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.DiscriminatorValue;

@Entity
@Table(name = "drivers")
@DiscriminatorValue("DRIVER")
public class Driver extends User {

    private String licenseNumber;
    private String vehicleDetails;

    public Driver() {
        super();
        setRole("DRIVER");
    }

    public Driver(String name, String email, String passwordHash, String licenseNumber) {
        super(name, email, passwordHash, "DRIVER");
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(String vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }
}
