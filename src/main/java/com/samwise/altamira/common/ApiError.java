package com.samwise.altamira.common;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ApiError(
        HttpStatus code,
        String field,
        String message
) {
}
