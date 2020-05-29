package com.dictionary.domain;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public interface ApplicationBootValidator extends ApplicationContextAware {

    void validate(ApplicationContext applicationContext);

    @Override
    default void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        validate(applicationContext);
    }
}
