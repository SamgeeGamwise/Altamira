package com.samwise.altamira.common;

import com.samwise.altamira.common.exceptions.BadRequestException;
import com.samwise.altamira.common.exceptions.ConflictException;
import com.samwise.altamira.common.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ApiError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiError(
                        HttpStatus.BAD_REQUEST,
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(
                        "Validation failed",
                        errors
                ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                        "Resource not found",
                        List.of(new ApiError(
                                HttpStatus.NOT_FOUND,
                                null,
                                ex.getMessage()
                        ))
                ));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflictException(
            ConflictException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(
                        "Conflicting data",
                        List.of(new ApiError(
                                HttpStatus.CONFLICT,
                                ex.getField(),
                                ex.getMessage()
                        ))
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidRequestBody(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(
                        "Invalid request body",
                        List.of(new ApiError(
                                HttpStatus.BAD_REQUEST,
                                null,
                                "Request body is malformed or contains invalid field types"
                        ))
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        List<ApiError> errors = ex.getConstraintViolations()
                .stream()
                .map(error -> new ApiError(
                        HttpStatus.BAD_REQUEST,
                        error.getPropertyPath().toString(),
                        error.getMessage()
                ))
                .toList();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(
                        "Validation failed",
                        errors
                ));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(
            BadRequestException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(
                        "Bad request",
                        List.of(new ApiError(
                                HttpStatus.BAD_REQUEST,
                                null,
                                ex.getMessage()
                        ))
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        "An unexpected error occurred",
                        List.of(new ApiError(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                null,
                                "Something went wrong"
                        ))
                ));
    }
}