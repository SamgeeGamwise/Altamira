package com.samwise.altamira.common;

import org.springframework.http.HttpStatus;

public record ApiError(
        HttpStatus code,
        String field,
        String message
) {
}
