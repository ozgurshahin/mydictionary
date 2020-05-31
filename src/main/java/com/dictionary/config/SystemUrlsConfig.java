package com.dictionary.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("system.urls")
public class SystemUrlsConfig {
    private String operatorPasswordRecovery;
}
