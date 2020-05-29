package com.dictionary.domain;

import com.dictionary.common.DomainEvent;
import com.dictionary.iam.User;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class UserCreatedEvent implements DomainEvent {
    private final User user;
}
