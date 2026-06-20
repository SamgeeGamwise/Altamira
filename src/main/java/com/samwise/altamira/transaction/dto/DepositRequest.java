package com.samwise.altamira.transaction.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositRequest(
    UUID accountId,
    BigDecimal amount,
    String description
) { }