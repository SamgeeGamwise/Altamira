package com.samwise.altamira.customer.dto;

import com.samwise.altamira.customer.domain.Customer;
import com.samwise.altamira.customer.domain.CustomerStatus;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        CustomerStatus status
) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getCustomerStatus()
        );
    }
}