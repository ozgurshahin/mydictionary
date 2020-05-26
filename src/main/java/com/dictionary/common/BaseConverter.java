package com.dictionary.common;

import org.springframework.core.convert.converter.Converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<S, T> implements Converter<S, T> {

    public List<T> convert(Collection<S> sources) {
        return sources.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
