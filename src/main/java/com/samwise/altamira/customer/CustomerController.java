package com.samwise.altamira.customer;

import com.samwise.altamira.common.ApiResponse;
import com.samwise.altamira.customer.dto.CreateCustomerRequest;
import com.samwise.altamira.customer.dto.CustomerLoginRequest;
import com.samwise.altamira.customer.dto.CustomerResponse;
import com.samwise.altamira.customer.dto.DeleteCustomerRequest;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomer(@PathVariable UUID id) {
        CustomerResponse customerResponse = customerService.getCustomer(id);
        return ApiResponse.success(customerResponse);
    }

    @PostMapping("/login")
    public ApiResponse<CustomerResponse> loginCustomer(@RequestBody CustomerLoginRequest createAccountRequest) {
        CustomerResponse customerResponse = customerService.getCustomerByEmail(createAccountRequest);
        return ApiResponse.success(customerResponse);
    }

    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
        CustomerResponse customerResponse = customerService.createCustomer(request);
        return ApiResponse.success(customerResponse);
    }

    @DeleteMapping
    public ApiResponse<Void> deleteCustomer(@RequestBody DeleteCustomerRequest request) {
        customerService.deleteCustomer(request);
        return ApiResponse.success(null);
    }

}
