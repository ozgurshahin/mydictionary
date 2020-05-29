package com.dictionary.domain.service;


import com.dictionary.ApplicationConfig;
import com.dictionary.domain.MessageBundleService;
import com.dictionary.infrastructure.i18n.ResourceMessageBundleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ResourceMessageBundleService.class, ApplicationConfig.class})
public class MessageBundleServiceTest {

    @Autowired
    private MessageBundleService messageBundleService;

    @Test
    public void should_not_return_message_id_if_the_message_is_available() {
        String messageKey = "exception.message.USER_NOT_FOUND_EXCEPTION";
        LocaleContextHolder.setLocale(Locale.forLanguageTag("tr"));
        String message = messageBundleService.getMessage(messageKey);
        Assert.assertNotEquals(messageKey, message);
    }

    @Test
    public void should_return_message_id_if_the_message_not_available() {
        String messageKey = "exception.message.xxx";
        String message = messageBundleService.getMessage(messageKey);
        Assert.assertEquals(messageKey, message);
    }
}

