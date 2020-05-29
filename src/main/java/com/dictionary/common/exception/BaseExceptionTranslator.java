package com.dictionary.common.exception;

import com.dictionary.domain.MessageBundleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RequiredArgsConstructor
public abstract class BaseExceptionTranslator {

    private final MessageBundleService messageBundleService;
    private final Environment environment;

    protected final ErrorResponse createSecurityError(String exceptionMessage, String trace, HttpStatus httpStatus) {
        String code = String.valueOf(httpStatus.value());
        String message = messageBundleService.getMessage(exceptionMessage, LocaleContextHolder.getLocale());

        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .trace(trace)
                .build();
    }

    protected final String getStackTrace(Exception e) {
        return (!ArrayUtils.contains(environment.getActiveProfiles(), "prod")) ? ExceptionUtils.getStackTrace(e) : null;
    }

    protected final String getLocalizedExceptionMessage(String key) {
        return messageBundleService.getMessage(key, LocaleContextHolder.getLocale());
    }

    protected final String getLocalizedExceptionMessage(String key, Map<String, Object> params) {
        return messageBundleService.getMessage(key, LocaleContextHolder.getLocale(), params);
    }

    protected final String getLocalizedValidationMessage() {
        return messageBundleService.getMessage("validation.message", LocaleContextHolder.getLocale());
    }
}
