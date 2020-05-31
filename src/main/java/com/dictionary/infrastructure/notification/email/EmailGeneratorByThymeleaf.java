package com.dictionary.infrastructure.notification.email;


import com.dictionary.common.EmailAddress;
import com.dictionary.domain.MessageBundleService;
import com.dictionary.domain.notification.email.Email;
import com.dictionary.domain.notification.email.EmailGenerator;
import com.dictionary.domain.notification.email.EmailType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
public class EmailGeneratorByThymeleaf implements EmailGenerator {
    private final SpringTemplateEngine templateEngine;
    private final MessageBundleService messageBundleService;

    @Override
    public Email generate(EmailAddress emailAddress, EmailType emailType, Map<String, Object> parameters) {
        List<EmailAddress> emailAddressList = Collections.singletonList(emailAddress);
        return generate(emailAddressList, emailType, parameters);
    }

    @Override
    public Email generate(List<EmailAddress> emailAddressList, EmailType emailType, Map<String, Object> parameters) {
        String subject = generateSubject(emailType);
        String senderName = messageBundleService.getMessage("email.sender");
        return new Email(emailType, emailAddressList, subject, senderName, LocaleContextHolder.getLocale().getLanguage());
    }

    private String generateBody(EmailType emailType, Map<String, Object> parameters) {
        Context context = new Context(LocaleContextHolder.getLocale());
        if (!CollectionUtils.isEmpty(parameters)) parameters.forEach(context::setVariable);
        return templateEngine.process(emailType.getTemplate(), context);
    }

    private String generateSubject(EmailType emailType) {
        String key = "email." + emailType.toString() + ".subject";
        return messageBundleService.getMessage(key);
    }
}
