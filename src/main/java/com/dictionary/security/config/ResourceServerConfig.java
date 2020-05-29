package com.dictionary.security.config;


import com.dictionary.iam.UserRole;
import com.dictionary.security.CustomAccessDeniedHandler;
import com.dictionary.security.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;
    private static final String[] WHITE_LIST = {
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/actuator/**"
    };

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(tokenServices())
                .resourceId("resource_id")
                .stateless(true);
        resources.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers()
                .xssProtection();

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(WHITE_LIST)
                .permitAll()
                .antMatchers("/**/admin/**").hasAnyAuthority(UserRole.OPERATOR.name())
                .antMatchers("/**/mock/**").hasAnyAuthority(UserRole.OPERATOR.name(), UserRole.USER.name())
                .antMatchers("/**/user/**").hasAnyAuthority(UserRole.USER.name());
    }

    @Bean
    public ResourceServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }
}
