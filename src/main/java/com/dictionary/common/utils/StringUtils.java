package com.dictionary.common.utils;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class StringUtils {

    public static String mask(String value, String maskChar, int leftClear, int rightClear) {
        if (isEmpty(value)) return value;

        int length = value.length();
        if (length <= leftClear + rightClear)
            return org.apache.commons.lang3.StringUtils.repeat(maskChar, length);

        String left = left(value, leftClear);
        String right = right(value, rightClear);

        int maskCharLength = length - leftClear - rightClear;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(left).append(org.apache.commons.lang3.StringUtils.repeat(maskChar, maskCharLength)).append(right);
        return stringBuilder.toString();

    }

    public static String right(final String value, final int length) {
        if (value == null) return null;

        if (length < 0) return "";

        if (value.length() <= length) return value;

        return value.substring(value.length() - length);
    }

    public static String left(final String value, final int length) {
        if (value == null) return null;

        if (length < 0) return "";

        if (value.length() <= length) return value;

        return value.substring(0, length);
    }

    public static boolean isNotEmpty(final String value) {
        return !org.springframework.util.StringUtils.isEmpty(value);
    }

    public static boolean isEmpty(final String value) {
        return org.springframework.util.StringUtils.isEmpty(value);
    }

    public static String replaceWithSubstitutor(String rawString, Map<String, Object> params, String prefix, String suffix) {
        return StringSubstitutor.replace(rawString, params, prefix, suffix);
    }
}
