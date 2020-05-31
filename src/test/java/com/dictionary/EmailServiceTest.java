package com.dictionary;


import com.dictionary.common.EmailAddress;
import com.dictionary.config.SystemUrlsConfig;
import com.dictionary.domain.notification.email.Email;
import com.dictionary.domain.notification.email.EmailType;
import com.dictionary.infrastructure.notification.email.EmailGeneratorByThymeleaf;
import com.dictionary.infrastructure.notification.email.SendGridEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles({"docker-local", "test"})
public class EmailServiceTest {

    @Autowired
    private SystemUrlsConfig systemUrlsConfig;

    @Autowired
    private EmailGeneratorByThymeleaf emailGeneratorByThymeleaf;

    @Autowired
    private SendGridEmailService sendGridEmailService;

    private final String guid = UUID.randomUUID().toString().replace("-", "");

    @Test
    public void testWelcomeOperatorEmail() {
        EmailAddress emailAddress = EmailAddress.parse("ozgurshahin@hotmail.com");
        Map<String, Object> params = new HashMap<>();
        params.put("credentialRecoveryToken", systemUrlsConfig.getOperatorPasswordRecovery().concat("/").concat(guid));
        params.put("fullName", "Jane Doe");

        Email email = emailGeneratorByThymeleaf.generate(emailAddress, EmailType.OPERATOR_CREATE, params);
        sendGridEmailService.sendEmail(email);

    }
}
