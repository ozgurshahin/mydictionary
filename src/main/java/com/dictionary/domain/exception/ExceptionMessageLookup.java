package com.dictionary.domain.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExceptionMessageLookup {

    private static final Map<String, String> value;

    static {
        Map<String, String> map = new HashMap<>();
        map.put("4000", "ENTITY_NOT_FOUND");

        map.put("6000", "USER_SERVICE_NOT_FOUND_EXCEPTION");
        map.put("6001", "FILE_STORAGE_EXCEPTION");
        map.put("6002", "PRE_SIGNED_URL");
        map.put("6003", "USER_NOT_FOUND_EXCEPTION");
        map.put("6004", "FILE_NOT_FOUND");
        map.put("6006","EMAIL_DELIVERY_FAILED");

        value = Collections.unmodifiableMap(map);
    }

    public static String getPropertyName(String code) {
        return value.get(code);
    }
}
