package com.dictionary.domain.exception;

import com.dictionary.domain.DomainException;
import com.dictionary.domain.ExceptionMessage;
import com.dictionary.domain.HttpStatus;

import java.util.Map;

@ExceptionMessage(code = "4000", value = "ENTITY_NOT_FOUND", httpStatus = HttpStatus.BAD_REQUEST)
public class EntityNotFoundException extends DomainException {

    public EntityNotFoundException(HttpStatus httpStatus, String errorCode, Map<String, Object> params) {
        super(httpStatus, errorCode, params);
    }
}
