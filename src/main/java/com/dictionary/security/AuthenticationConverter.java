package com.dictionary.security;


import com.dictionary.common.BaseConverter;
import com.dictionary.iam.Principle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Slf4j
@Component
public class AuthenticationConverter extends BaseConverter<Authentication, Principle> {

    @Override
    public Principle convert(Authentication source) {
        if (Objects.nonNull(source) && OAuth2Authentication.class.isAssignableFrom(source.getClass())) {
            return convert((OAuth2Authentication) source);
        }

        throw new RuntimeException(new AccessDeniedException("Authentication converter not found"));
    }

    private Principle convert(OAuth2Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        return Principle.builder()
                .clientId(authentication.getOAuth2Request().getClientId())
                .remoteAddress(details.getRemoteAddress())
                .username(authentication.isClientOnly() ? authentication.getOAuth2Request().getClientId() : authentication.getUserAuthentication().getName())
                .authenticated(authentication.isAuthenticated())
                .build();
    }
}
