package com.samwise.altamira.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Account {
    private UUID id;
    private UUID customerId;
    private int accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private Date createdAt;
    private Date updatedAt;
}
