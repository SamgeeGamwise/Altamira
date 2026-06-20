package com.samwise.altamira.transaction;

import com.samwise.altamira.common.ApiResponse;
import com.samwise.altamira.customer.CustomerService;
import com.samwise.altamira.customer.dto.CustomerLoginRequest;
import com.samwise.altamira.customer.dto.CustomerResponse;
import com.samwise.altamira.transaction.dto.DepositRequest;
import com.samwise.altamira.transaction.dto.TransactionResponse;
import com.samwise.altamira.transaction.dto.WithdrawalRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ApiResponse<TransactionResponse> getTransaction(@PathVariable UUID id) {
        TransactionResponse transactionResponse = transactionService.getTransaction(id);
        return ApiResponse.success(transactionResponse);
    }

    @PostMapping("/withdraw")
    public ApiResponse<TransactionResponse> createWithdrawal(@RequestBody WithdrawalRequest request) {
        TransactionResponse transactionResponse = transactionService.createWithdrawal(request);
        return ApiResponse.success(transactionResponse);
    }

    @PostMapping("/deposit")
    public ApiResponse<TransactionResponse> createDeposit(@RequestBody DepositRequest request) {
        TransactionResponse transactionResponse = transactionService.createDeposit(request);
        return ApiResponse.success(transactionResponse);
    }
}
