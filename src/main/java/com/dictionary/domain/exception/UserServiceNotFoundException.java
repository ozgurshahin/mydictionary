package com.dictionary.domain.exception;

import com.dictionary.domain.DomainException;
import com.dictionary.domain.ExceptionMessage;
import com.dictionary.domain.HttpStatus;

@ExceptionMessage(code = "6000", value = "USER_SERVICE_NOT_FOUND_EXCEPTION", httpStatus = HttpStatus.BAD_REQUEST)
public class UserServiceNotFoundException extends DomainException {
}
