package com.dictionary.security;


import com.dictionary.iam.CurrentPrinciple;
import com.dictionary.iam.Principle;
import com.dictionary.iam.User;
import com.dictionary.iam.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
public class CurrentPrincipalProvider implements CurrentPrinciple {

    private final UserRepository userRepository;
    private final AuthenticationConverter authenticationConverter;

    @Override
    public Principle getPrinciple() {
        SecurityContext context = SecurityContextHolder.getContext();
        return authenticationConverter.convert(context.getAuthentication());
    }

    @Override
    public User getUser() throws Exception {
        return userRepository.getUser(getPrinciple().getUsername());
    }

}
