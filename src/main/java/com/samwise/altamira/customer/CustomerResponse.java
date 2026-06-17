package com.samwise.altamira.customer;

import java.util.UUID;

public class CustomerResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

    public CustomerResponse(UUID id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}