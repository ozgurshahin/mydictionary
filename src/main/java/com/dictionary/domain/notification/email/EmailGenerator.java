package com.dictionary.domain.notification.email;

import com.dictionary.common.EmailAddress;

import java.util.List;
import java.util.Map;

public interface EmailGenerator {
    Email generate(EmailAddress emailAddress, EmailType emailType, Map<String, Object> parameters);

    Email generate(List<EmailAddress> emailAddressList, EmailType emailType, Map<String, Object> parameters);
}
