package com.dictionary.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
public final class DomainEvents {

    private final org.springframework.context.ApplicationEventPublisher publisher;
    private static org.springframework.context.ApplicationEventPublisher _publisher;

    @PostConstruct
    public void init() {
        _publisher = publisher;
    }

    public static <T> void raise(T event) {
        if (_publisher != null)
            _publisher.publishEvent(event);
    }
}
