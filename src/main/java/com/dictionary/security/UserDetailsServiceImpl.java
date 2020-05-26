package com.dictionary.security;


import com.dictionary.iam.User;
import com.dictionary.iam.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService, ClientDetailsService {
    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(), Arrays.asList(user.getRole()));
    }

    @SneakyThrows
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return new CustomClientDetails(getUser(clientId));
    }

    private User getUser(String email) throws Exception {
        return userRepository.getUser(email);
    }
}
