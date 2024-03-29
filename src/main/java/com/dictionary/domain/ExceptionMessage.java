package com.dictionary.domain;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
public @interface ExceptionMessage {

    String value();

    String code();

    HttpStatus httpStatus() default HttpStatus.NULL;
}
