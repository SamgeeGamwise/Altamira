package com.samwise.altamira.customer.dto;

public record CreateCustomerRequest(
        String firstName,
        String lastName,
        String email
) { }