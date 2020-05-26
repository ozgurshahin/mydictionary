package com.dictionary.security;

import com.dictionary.iam.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

@RequiredArgsConstructor
public class CustomClientDetails implements ClientDetails {

    private final User user;

    @Override
    public String getClientId() {
        return user.getUserName();
    }

    @Override
    public Set<String> getResourceIds() {
        HashSet<String> resourceIds = new HashSet<>();
        resourceIds.add(String.valueOf(user.getId()));
        resourceIds.add("resource_id");
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return user.getPassword();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        HashSet<String> scopes = new HashSet<>();
        scopes.add("read");
        scopes.add("write");
        return scopes;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        HashSet<String> grantTypes = new HashSet<>();
        grantTypes.add("client_credentials");
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return new HashSet<>();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Arrays.asList(user.getRole());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 3000;//todo
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 3000;//todo
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }
}
