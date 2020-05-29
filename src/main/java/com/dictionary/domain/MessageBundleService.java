package com.dictionary.domain;

import java.util.Locale;
import java.util.Map;

public interface MessageBundleService {
    String getMessage(String messageKey, Object... params);

    String getMessage(String messageKey, Locale locale, Object... params);

    String getMessage(String messageKey, Map<String, Object> params);

    String getMessage(String messageKey, Locale locale, Map<String, Object> params);
}
