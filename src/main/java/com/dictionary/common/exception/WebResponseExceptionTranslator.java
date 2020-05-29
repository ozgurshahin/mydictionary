package com.dictionary.common.exception;

import com.dictionary.domain.MessageBundleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebResponseExceptionTranslator extends BaseExceptionTranslator implements org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator {

    public WebResponseExceptionTranslator(MessageBundleService messageBundleService, Environment environment) {
        super(messageBundleService, environment);
    }

    @Override
    public ResponseEntity<ErrorResponse> translate(Exception ex) {
        HttpStatus httpStatus = (ex instanceof OAuth2Exception) ? HttpStatus.valueOf(((OAuth2Exception) ex).getHttpErrorCode()) : HttpStatus.UNAUTHORIZED;
        String messageKey = getMessageKey(httpStatus);
        String trace = getStackTrace(ex);

        ErrorResponse errorResponse = createSecurityError(messageKey, trace, httpStatus);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private String getMessageKey(HttpStatus httpStatus) {
        switch (httpStatus) {
            case BAD_REQUEST:
                return "exception.message.BAD_REQUEST";
            case FORBIDDEN:
                return "exception.message.FORBIDDEN";
            default:
                return "exception.message.UNAUTHORIZED";
        }
    }
}
