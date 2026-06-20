package com.samwise.altamira.account;

import com.samwise.altamira.account.domain.Account;
import com.samwise.altamira.account.domain.AccountStatus;
import com.samwise.altamira.account.dto.AccountResponse;
import com.samwise.altamira.account.dto.CreateAccountRequest;
import com.samwise.altamira.account.dto.DeleteAccountRequest;
import com.samwise.altamira.common.exceptions.BadRequestException;
import com.samwise.altamira.common.exceptions.NotFoundException;
import com.samwise.altamira.customer.domain.Customer;
import com.samwise.altamira.customer.CustomerRepository;
import com.samwise.altamira.customer.domain.CustomerStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }
    private long generateUniqueAccountNumber() {
        long accountNumber;

        do {
            accountNumber = ThreadLocalRandom.current()
                    .nextInt(100_000_000, 1_000_000_000);
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
    public AccountResponse getAccount(UUID id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        return AccountResponse.from(account);
    }

    public Account getActiveAccountOrThrow(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new BadRequestException("Account is not active");
        }

        if (account.getCustomer().getCustomerStatus() != CustomerStatus.ACTIVE) {
            throw new BadRequestException("Customer is inactive");
        }

        return account;
    }

    public AccountResponse createAccount(CreateAccountRequest request) {
        long accountNumber = generateUniqueAccountNumber();
        Customer customer = customerRepository
                .findById(request.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        Account account = new Account(
                customer,
                accountNumber,
                request.accountType()
        );

        Account savedAccount = accountRepository.save(account);

        return AccountResponse.from(savedAccount);
    }

    public void deleteAccount(DeleteAccountRequest request) {
        Account account = accountRepository
                .findById(request.id())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        account.setAccountStatus(AccountStatus.CLOSED);

        accountRepository.save(account);
    }
}
