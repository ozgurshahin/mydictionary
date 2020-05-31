package com.dictionary.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

@Value
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
@Access(AccessType.FIELD)
public class EmailAddress {

    @Email
    private final String value;

    public static EmailAddress parse(String value) {
        return new EmailAddress(value.trim().toLowerCase().replace(" ", ""));
    }
}
