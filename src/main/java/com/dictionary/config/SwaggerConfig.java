package com.dictionary.config;

import com.dictionary.common.AdminApi;
import com.dictionary.common.UserApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.AlternateTypePropertyBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.configuration.ObjectMapperConfigured;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Configuration
@RequiredArgsConstructor
@EnableSwagger2
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig implements ApplicationListener<ObjectMapperConfigured> {

    private static final String ADMIN_API_VERSION = "v1";
    private static final String USER_API_VERSION = "v1";
    private static final String TOKEN_URL = "/api/oauth/token";
    private final TypeResolver typeResolver;
    private ObjectMapper objectMapper;

    @Bean
    public Docket adminApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Admin API")
                .version(ADMIN_API_VERSION)
                .build();

        GrantType clientCredentialsGrant = new ClientCredentialsGrant(TOKEN_URL);

        return configureDocket()
                .apiInfo(apiInfo)
                .groupName("Admin  API")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(AdminApi.class))
                .build()
                .securitySchemes(Collections.singletonList(oauth2SecuritySchema(clientCredentialsGrant)))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    @Bean
    public Docket userApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("User  API")
                .version(USER_API_VERSION)
                .build();

        GrantType clientCredentialsGrant = new ClientCredentialsGrant(TOKEN_URL);

        return configureDocket()
                .apiInfo(apiInfo)
                .groupName("User API")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(UserApi.class))
                .build()
                .securitySchemes(Collections.singletonList(oauth2SecuritySchema(clientCredentialsGrant)))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private Docket configureDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE)))
                .consumes(new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE)))
                .directModelSubstitute(ZoneId.class, String.class)
                .directModelSubstitute(Date.class, Long.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, Long.class);
//                );
    }

    private OAuth oauth2SecuritySchema(GrantType grantType) {
        return new OAuth("oauth2", Collections.emptyList(), Collections.singletonList(grantType));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(securityReferences()).forPaths(PathSelectors.regex("/.*")).build();
    }

    private List<SecurityReference> securityReferences() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "accessEverything")};
        return Arrays.asList(new SecurityReference("authorization", authorizationScopes), new SecurityReference("oauth2", authorizationScopes));
    }

    private AlternateTypePropertyBuilder property(Class<?> type, String name) {
        return new AlternateTypePropertyBuilder()
                .withName(name)
                .withType(type)
                .withCanRead(true)
                .withCanWrite(true);
    }

    @Override
    public void onApplicationEvent(ObjectMapperConfigured event) {
        this.objectMapper = event.getObjectMapper();
    }
}
