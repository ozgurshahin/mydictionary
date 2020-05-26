package com.dictionary.common;

import com.dictionary.iam.User;
import com.dictionary.iam.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserAuditorAware implements AuditorAware<User> {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) return Optional.empty();

        if (AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return Optional.empty();
        }

        if (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return Optional.empty();
        }

        if (!authentication.isAuthenticated()) return Optional.empty();

        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext()
                .getAuthentication();

        if (Objects.isNull(oAuth2Authentication)) return Optional.empty();


        String username = oAuth2Authentication.getOAuth2Request().getClientId();

        Optional<User> user = userRepository.getByActiveUserInNewTrn(username);

        log.info("Current audited user : {}", user);

        return user;
    }
}
