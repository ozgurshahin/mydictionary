package com.dictionary.infrastructure.notification.email;


import com.dictionary.domain.DomainException;
import com.dictionary.domain.ExceptionMessage;

@ExceptionMessage(code = "6006", value = "EMAIL_DELIVERY_FAILED")
public class EmailDeliveryFailedException extends DomainException {
}
