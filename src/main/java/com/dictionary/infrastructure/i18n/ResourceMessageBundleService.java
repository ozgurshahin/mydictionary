package com.dictionary.infrastructure.i18n;

import com.dictionary.common.utils.StringUtils;
import com.dictionary.domain.MessageBundleService;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Service
public class ResourceMessageBundleService implements MessageBundleService {

    private final MessageSourceAccessor messageSourceAccessor;

    public ResourceMessageBundleService(MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    @Override
    public String getMessage(String messageKey, Object... params) {
        String rawMessage = getMessage(messageKey);
        return format(rawMessage, params);
    }

    @Override
    public String getMessage(String messageKey, Locale locale, Object... params) {
        String rawMessage = getMessage(messageKey, locale);
        return format(rawMessage, params);
    }

    @Override
    public String getMessage(String messageKey, Map<String, Object> params) {
        String rawMessage = getMessage(messageKey);
        return format(rawMessage, params);
    }

    @Override
    public String getMessage(String messageKey, Locale locale, Map<String, Object> params) {
        String rawMessage = getMessage(messageKey, locale);
        return format(rawMessage, params);
    }

    private String getMessage(String messageKey) {
        return getMessage(messageKey, LocaleContextHolder.getLocale());
    }

    private String getMessage(String messageKey, Locale locale) {
        try {
            return messageSourceAccessor.getMessage(messageKey, locale);
        } catch (NoSuchMessageException e) {
            return messageKey;
        }
    }

    private String format(String rawMessage, Object... params) {
        if (Objects.isNull(params) || params.length == 0)
            return rawMessage;

        return MessageFormat.format(rawMessage, params);
    }

    private String format(String rawMessage, Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return rawMessage;
        }
        return StringUtils.replaceWithSubstitutor(rawMessage, params, "{", "}");
    }
}
