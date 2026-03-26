package com.tourme.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.DiscriminatorValue;

@Entity
@Table(name = "tourists")
@DiscriminatorValue("TOURIST")
public class Tourist extends User {

    public Tourist() {
        super();
        setRole("TOURIST");
    }

    public Tourist(String name, String email, String passwordHash) {
        super(name, email, passwordHash, "TOURIST");
    }
}
