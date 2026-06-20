package com.samwise.altamira.transaction.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        UUID transferFromAccountId,
        UUID transferToAccountId,
        BigDecimal amount,
        String description
) { }