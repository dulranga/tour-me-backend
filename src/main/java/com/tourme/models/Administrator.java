package com.tourme.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.DiscriminatorValue;

@Entity
@Table(name = "administrators")
@DiscriminatorValue("ADMINISTRATOR")
public class Administrator extends User {

    public Administrator() {
        super();
        setRole("ADMINISTRATOR");
    }

    public Administrator(String name, String email, String passwordHash) {
        super(name, email, passwordHash, "ADMINISTRATOR");
    }
}
