package com.samwise.altamira.transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private UUID accountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String description;
    private Date createdAt;
}
