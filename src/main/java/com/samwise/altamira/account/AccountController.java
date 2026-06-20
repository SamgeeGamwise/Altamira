package com.samwise.altamira.account;

import com.samwise.altamira.account.dto.AccountResponse;
import com.samwise.altamira.account.dto.CreateAccountRequest;
import com.samwise.altamira.account.dto.DeleteAccountRequest;
import com.samwise.altamira.common.ApiResponse;
import com.samwise.altamira.transaction.TransactionService;
import com.samwise.altamira.transaction.dto.TransactionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ApiResponse<AccountResponse> getAccount(@PathVariable UUID id) {
        AccountResponse accountResponse = accountService.getAccount(id);
        return ApiResponse.success(accountResponse);
    }

    @GetMapping("/{id}/transactions")
    public ApiResponse<List<TransactionResponse>> getAllTransaction(@PathVariable UUID id) {
        List<TransactionResponse> transactionsResponse = transactionService.getAccountTransactions(id);
        return ApiResponse.success(transactionsResponse);
    }

    @PostMapping
    public ApiResponse<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        AccountResponse accountResponse = accountService.createAccount(request);
        return ApiResponse.success(accountResponse);
    }

    @DeleteMapping
    public ApiResponse<Void> deleteAccount(@RequestBody DeleteAccountRequest request) {
        accountService.deleteAccount(request);
        return ApiResponse.success(null);
    }
}