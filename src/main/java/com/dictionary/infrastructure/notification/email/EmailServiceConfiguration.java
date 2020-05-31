package com.dictionary.infrastructure.notification.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "email.sendgrid")
public class EmailServiceConfiguration {
    private String apiKey;
    private String url;
    private String from;
}
