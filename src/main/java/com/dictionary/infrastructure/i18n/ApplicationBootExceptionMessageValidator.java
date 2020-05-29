package com.dictionary.infrastructure.i18n;


import com.dictionary.common.utils.ApplicationReflectionUtil;
import com.dictionary.domain.ApplicationBootValidator;
import com.dictionary.domain.DomainException;
import com.dictionary.domain.ExceptionMessage;
import com.dictionary.domain.exception.ExceptionMessageLookup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class ApplicationBootExceptionMessageValidator implements ApplicationBootValidator {

    private final MessageSourceAccessor messageSourceAccessor;

    public ApplicationBootExceptionMessageValidator(MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void validate(ApplicationContext applicationContext) {
        ApplicationReflectionUtil.getSubClasses(DomainException.class, "io.apm")
                .forEach(this::validate);
    }

    private void validate(Class<? extends DomainException> exceptionClass) {
        log.info("Verifying exception message structure. clazz = {} ", exceptionClass.getName());
        ExceptionMessage exceptionMessage = AnnotationUtils.findAnnotation(exceptionClass, ExceptionMessage.class);

        if (Objects.isNull(exceptionMessage))
            throw new RuntimeException("You need to specify ExceptionMessage annotation for this class : " + exceptionClass.getSimpleName());

        checkExceptionCode(exceptionMessage);
        checkLanguages(exceptionMessage);
    }

    private void checkExceptionCode(ExceptionMessage exceptionMessage) {
        String code = exceptionMessage.code();
        String messageProperty = exceptionMessage.value();
        String lookup = ExceptionMessageLookup.getPropertyName(code);

        if (StringUtils.isEmpty(lookup))
            throw new RuntimeException("Code : " + code + " not found in ExceptionMessageLookup");

        if (!messageProperty.equals(lookup))
            throw new RuntimeException("Message property must be equals code(" + code + ") in lookup");
    }

    private void checkLanguages(ExceptionMessage exceptionMessage) {
        String messageSourceKey = exceptionMessage.value();
        messageSourceAccessor.getMessage("exception.message." + messageSourceKey, LocaleContextHolder.getLocale());
    }

}
