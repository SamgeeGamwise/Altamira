package com.samwise.altamira.account.dto;
import com.samwise.altamira.account.domain.AccountType;

import java.util.UUID;

public record CreateAccountRequest (
    UUID customerId,
    AccountType accountType
) { }
