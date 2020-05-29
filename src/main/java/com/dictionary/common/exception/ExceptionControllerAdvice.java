package com.dictionary.common.exception;


import com.dictionary.domain.DomainException;
import com.dictionary.domain.ExceptionMessage;
import com.dictionary.domain.MessageBundleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice extends BaseExceptionTranslator {

    public ExceptionControllerAdvice(MessageBundleService messageBundleService, Environment environment) {
        super(messageBundleService, environment);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> sendError(Exception ex) {
        log.error(ex.getMessage(), ex);
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        HttpStatus status = responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        String code = Objects.nonNull(responseStatus) ? String.valueOf(responseStatus.code().value()) : String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String message = StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : "No message available";
        return buildErrorResponseEntity(ex, status, code, message);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> sendError(DomainException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionMessage exceptionMessage = AnnotationUtils.findAnnotation(ex.getClass(), ExceptionMessage.class);
        HttpStatus status = getHttpStatus(exceptionMessage, ex);
        String code = getCode(exceptionMessage, ex);
        String message = getLocalizedExceptionMessage("exception.message." + exceptionMessage.value(), ex.getParams());
        return buildErrorResponseEntity(ex, status, code, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> processValidationErrors(MethodArgumentNotValidException ex) {
        return processBindingResult(ex.getBindingResult(), ex);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> processValidationErrors(BindException ex) {
        return processBindingResult(ex.getBindingResult(), ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> processAccessDeniedException(AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponse errorResponse = createSecurityError("exception.message.FORBIDDEN", getStackTrace(ex), HttpStatus.FORBIDDEN);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> processAuthentication(AuthenticationException ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponse errorResponse = createSecurityError("exception.message.UNAUTHORIZED", getStackTrace(ex), HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> processBindingResult(BindingResult result, Exception ex) {
        log.error(ex.getMessage(), ex);
        String trace = getStackTrace(ex);
        List<FieldErrorDTO> validationMessage = processFieldErrors(result.getFieldErrors());
        String message;
        if (CollectionUtils.isEmpty(validationMessage)) {
            message = getLocalizedValidationMessage();
        } else {
            message = StringUtils.isNotEmpty(validationMessage.get(0).getMessage()) ? validationMessage.get(0).getMessage() : getLocalizedValidationMessage();
        }
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("90000")
                .message(message)
                .validationMessages(validationMessage)
                .trace(trace)
                .build();
        log.error("validation errorResponse = {}", errorResponse);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponseEntity(Exception ex, HttpStatus status, String code, String message) {
        String trace = getStackTrace(ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(code)
                .message(message)
                .trace(trace)
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    private HttpStatus getHttpStatus(ExceptionMessage exceptionMessage, DomainException ex) {
        if (Objects.nonNull(ex.getHttpStatus()))
            return HttpStatus.valueOf(ex.getHttpStatus().name());

        if (com.dictionary.domain.HttpStatus.NULL.equals(exceptionMessage.httpStatus()))
            return HttpStatus.INTERNAL_SERVER_ERROR;

        return HttpStatus.valueOf(exceptionMessage.httpStatus().name());
    }

    private String getCode(ExceptionMessage exceptionMessage, DomainException ex) {
        if (StringUtils.isNotEmpty(ex.getErrorCode()))
            return ex.getErrorCode();

        return exceptionMessage.code();
    }

    private List<FieldErrorDTO> processFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream().map(this::convert).collect(Collectors.toList());
    }

    private FieldErrorDTO convert(FieldError fieldError) {
        return new FieldErrorDTO(fieldError.getField(), resolveLocalizedErrorMessage(fieldError));
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        String localizedErrorMessage = getLocalizedExceptionMessage(fieldError.getDefaultMessage());

        if (localizedErrorMessage.equals(fieldError.getDefaultMessage()))
            localizedErrorMessage = fieldError.getDefaultMessage();

        return localizedErrorMessage;
    }
}
