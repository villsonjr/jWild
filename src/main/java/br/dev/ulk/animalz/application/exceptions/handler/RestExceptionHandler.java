package br.dev.ulk.animalz.application.exceptions.handler;

import br.dev.ulk.animalz.application.exceptions.AuthenticationFailedException;
import br.dev.ulk.animalz.application.exceptions.ExpiredTokenException;
import br.dev.ulk.animalz.application.exceptions.InternalServerException;
import br.dev.ulk.animalz.application.exceptions.InvalidCredentialsException;
import br.dev.ulk.animalz.application.exceptions.InvalidHeaderException;
import br.dev.ulk.animalz.application.exceptions.InvalidRoleException;
import br.dev.ulk.animalz.application.exceptions.InvalidTokenException;
import br.dev.ulk.animalz.application.exceptions.ResourceAlreadyRegisteredException;
import br.dev.ulk.animalz.application.exceptions.ResourceNotFoundException;
import br.dev.ulk.animalz.application.exceptions.payloads.ApiError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                httpServletRequest.getRequestURI(),
                ex.getMessage(),
                List.of(new ApiError.ApiSubError("resource", "The requested resource could not be found."))
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ApiError> handleInvalidRoleException(InvalidRoleException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Role invalid",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyRegisteredException.class)
    public ResponseEntity<ApiError> handleResourceAlreadyRegisteredException(ResourceAlreadyRegisteredException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Resource Already Registered",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ApiError> handleAuthenticationFailedException(AuthenticationFailedException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Unauthorized - Authentication failed",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ApiError> handleInternalServerException(InternalServerException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                request.getDescription(false),
                "An unexpected error occurred",
                List.of(new ApiError.ApiSubError("exception", ex.getMessage()))
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMalformedJsonException(HttpMessageNotReadableException ex) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                httpServletRequest.getRequestURI(),
                "Malformed JSON request",
                List.of(
                        new ApiError.ApiSubError("json", "The JSON request body is malformed or invalid."),
                        new ApiError.ApiSubError("message", ex.getMessage())
                )
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict - Email or username already exists",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidCredentialsException.class, BadCredentialsException.class})
    public ResponseEntity<ApiError> handleInvalidCredentials(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized - Invalid credentials",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<ApiError.ApiSubError> subErrors = ex.getConstraintViolations().stream()
                .map(violation -> new ApiError.ApiSubError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                )).toList();
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Constraint Violation",
                request.getDescription(false),
                "One or more constraint violations occurred",
                subErrors
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        List<ApiError.ApiSubError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiError.ApiSubError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                httpServletRequest.getRequestURI(),
                "Validation failed",
                fieldErrors
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ApiError> handleAccountStatusException(AccountStatusException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Unauthorized - The account is locked",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Unauthorized - You are not authorized to access this resource",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiError> handleSignatureException(SignatureException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Unauthorized - The JWT signature is invalid",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Unauthorized - The JWT token is invalid",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Unauthorized -The JWT token has expired",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ApiError> handleExpiredTokenException(ExpiredTokenException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Unauthorized -The JWT token has expired",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidHeaderException.class)
    public ResponseEntity<ApiError> handleInvalidHeaderException(InvalidHeaderException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Unauthorized - Header Invalid",
                request.getDescription(false),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
}