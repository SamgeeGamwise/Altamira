package com.samwise.altamira.customer;

import com.samwise.altamira.account.CreateAccountRequest;
import com.samwise.altamira.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getAccount(@PathVariable UUID id) {
        return ApiResponse.success(
            new CustomerResponse(id, "Joe", "Bob", "joe@bob.com")
        );
    }

    @PostMapping("/login")
    public ApiResponse<CustomerResponse> login(@RequestBody CustomerLoginRequest createAccountRequest) {
        UUID id = UUID.randomUUID();

        return ApiResponse.success(
            new CustomerResponse(id, "Joe", "Bob", createAccountRequest.getEmail())
        );
    }

    @PostMapping
    public ApiResponse<CustomerResponse> createAccount(@RequestBody CreateCustomerRequest request) {
        UUID id = UUID.randomUUID();

        return ApiResponse.success(
            new CustomerResponse(id, request.getFirstName(), request.getLastName(), request.getEmail())
        );
    }
}
