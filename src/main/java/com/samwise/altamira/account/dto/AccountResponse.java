package com.samwise.altamira.account.dto;

import com.samwise.altamira.account.domain.Account;
import com.samwise.altamira.account.domain.AccountStatus;
import com.samwise.altamira.account.domain.AccountType;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        AccountType accountType,
        AccountStatus accountStatus,
        BigDecimal balance
) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountType(),
                account.getAccountStatus(),
                account.getBalance()
        );
    }
}
