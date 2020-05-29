package com.dictionary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Map;

@AllArgsConstructor
@Getter
public abstract class DomainException extends RuntimeException {
    public DomainException() {
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private Map<String, Object> params;

}
