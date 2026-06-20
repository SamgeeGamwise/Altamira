package com.samwise.altamira.transaction.dto;

import com.samwise.altamira.transaction.domain.Transaction;
import com.samwise.altamira.transaction.domain.TransactionType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record TransactionResponse(
    UUID id,
    UUID accountId,
    TransactionType transactionType,
    BigDecimal amount,
    String description
) {
    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getDescription()
        );
    }

    public static List<TransactionResponse> fromList(List<Transaction> transactions) {
        return transactions.stream().map(TransactionResponse::from).toList();
    }
}

