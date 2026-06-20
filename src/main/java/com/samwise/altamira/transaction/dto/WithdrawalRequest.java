package com.samwise.altamira.transaction.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawalRequest(
        UUID accountId,
        BigDecimal amount,
        String description
) { }