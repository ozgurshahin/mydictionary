package com.dictionary.domain.exception;


import com.dictionary.domain.DomainException;
import com.dictionary.domain.ExceptionMessage;
import com.dictionary.domain.HttpStatus;

@ExceptionMessage(code = "6003", value = "USER_NOT_FOUND_EXCEPTION", httpStatus = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends DomainException {
}
