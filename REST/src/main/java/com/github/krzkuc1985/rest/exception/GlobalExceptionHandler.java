package com.github.krzkuc1985.rest.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    protected final static String ERROR_NOT_FOUND = "ERROR.GENERAL.NOT_FOUND";
    protected final static String ERROR_BAD_REQUEST = "ERROR.GENERAL.BAD_REQUEST";
    protected final static String ERROR_CONFLICT = "ERROR.GENERAL.CONFLICT";
    protected final static String ERROR_OPTIMISTIC_LOCKING_FAILURE = "ERROR.GENERAL.OPTIMISTIC_LOCKING_FAILURE";

    @ExceptionHandler(EntityNotFoundException.class)
    public static ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ERROR_NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ERROR_BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, ERROR_CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, ERROR_OPTIMISTIC_LOCKING_FAILURE, ex.getMessage(), request);
    }

    private static ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, String details, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .details(details)
                .path(request.getDescription(false))
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }
}


