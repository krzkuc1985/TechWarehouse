package com.github.krzkuc1985.rest.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.AuthenticationException;
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
    protected final static String ERROR_JWT_EXPIRED = "ERROR.JWT.EXPIRED";
    protected final static String ERROR_JWT_MALFORMED = "ERROR.JWT.MALFORMED";
    protected final static String ERROR_JWT_UNSUPPORTED = "ERROR.JWT.UNSUPPORTED";
    protected final static String ERROR_JWT_SIGNATURE = "ERROR.JWT.SIGNATURE";
    protected final static String ERROR_GENERAL_ILLEGAL_ARGUMENT = "ERROR.GENERAL.ILLEGAL_ARGUMENT";
    protected final static String ERROR_AUTHENTICATION = "ERROR.AUTHENTICATION";

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

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ERROR_JWT_EXPIRED, ex.getMessage(), request);
    }

    @ExceptionHandler(io.jsonwebtoken.MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(io.jsonwebtoken.MalformedJwtException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ERROR_JWT_MALFORMED, ex.getMessage(), request);
    }

    @ExceptionHandler(io.jsonwebtoken.UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedJwtException(io.jsonwebtoken.UnsupportedJwtException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ERROR_JWT_UNSUPPORTED, ex.getMessage(), request);
    }

    @ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(io.jsonwebtoken.security.SignatureException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ERROR_JWT_SIGNATURE, ex.getMessage(), request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ERROR_GENERAL_ILLEGAL_ARGUMENT, ex.getMessage(), request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ERROR_AUTHENTICATION, ex.getMessage(), request);
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


