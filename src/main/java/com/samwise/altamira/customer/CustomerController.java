package com.samwise.altamira.customer;

import com.samwise.altamira.account.CreateAccountRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @GetMapping("/{id}")
    public CustomerResponse getAccount(@PathVariable UUID id) {
        return new CustomerResponse(id, "Joe", "Bob", "joe@bob.com");
    }

    @PostMapping
    public CustomerResponse createAccount(@RequestBody CreateAccountRequest request) {
        UUID id = UUID.randomUUID();

        return new CustomerResponse(id, request.getFirstName(), request.getLastName(), request.getEmail());
    }
}
