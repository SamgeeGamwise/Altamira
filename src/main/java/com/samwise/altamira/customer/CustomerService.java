package com.samwise.altamira.customer;

import com.samwise.altamira.common.exceptions.BadRequestException;
import com.samwise.altamira.common.exceptions.ConflictException;
import com.samwise.altamira.common.exceptions.NotFoundException;
import com.samwise.altamira.customer.domain.Customer;
import com.samwise.altamira.customer.domain.CustomerStatus;
import com.samwise.altamira.customer.dto.CreateCustomerRequest;
import com.samwise.altamira.customer.dto.CustomerLoginRequest;
import com.samwise.altamira.customer.dto.CustomerResponse;
import com.samwise.altamira.customer.dto.DeleteCustomerRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer getActiveCustomerOrThrow(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        if (customer.getCustomerStatus() != CustomerStatus.ACTIVE) {
            throw new BadRequestException("Customer is inactive");
        }

        return customer;
    }

    public CustomerResponse getCustomer(UUID id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        return CustomerResponse.from(customer);
    }

    public CustomerResponse getCustomerByEmail(CustomerLoginRequest customerLoginRequest) {
        Customer customer = customerRepository
                .findByEmail(customerLoginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        return CustomerResponse.from(customer);
    }
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new ConflictException("Customer already exists with provided email", "email");
        }

        Customer customer = new Customer(
                request.firstName(),
                request.lastName(),
                request.email()
        );

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerResponse.from(savedCustomer);
    }

    public void deleteCustomer(DeleteCustomerRequest request) {
        Customer customer = customerRepository
                .findById(request.id())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        customer.setCustomerStatus(CustomerStatus.INACTIVE);

        customerRepository.save(customer);
    }
}
