package com.nagendra.ProductService.config;

import com.nagendra.ProductService.constants.OpenApiConfigurationConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class OpenApiConfig {

    private static final String BEARER = "bearer";
    private static final String BEARER_FORMAT = "JWT";
    private static final String AUTH_TYPE = "bearerAuth";

    /**
     * Open API Configuration
     *
     * @return OAPS3 Bean With Provided Configuration Details
     */
    @Bean
    public OpenAPI denOrderOpenAPI() {

        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.put(AUTH_TYPE, new ArrayList<>());

        return new OpenAPI().components(getComponentWithSecurity()).info(new Info()
                        .title(OpenApiConfigurationConstants.TITLE).description(OpenApiConfigurationConstants.DESCRIPTION)
                        .version(OpenApiConfigurationConstants.VERSION)
                        .license(new License().name(OpenApiConfigurationConstants.LICENSE)
                                .url(OpenApiConfigurationConstants.NAG_URL))
                        .contact(new Contact().name(OpenApiConfigurationConstants.NAG_CAH)
                                .url(OpenApiConfigurationConstants.NAG_URL).email(OpenApiConfigurationConstants.NAG_MAIL)))
                .security(Arrays.asList(securityRequirement));
    }

    /**
     * Get components with security configuration for JWT
     *
     * @return
     */
    private Components getComponentWithSecurity() {

        Components components = new Components();

        SecurityScheme scheme = new SecurityScheme();
        scheme.setType(SecurityScheme.Type.HTTP);
        scheme.setScheme(BEARER);
        scheme.setBearerFormat(BEARER_FORMAT);

        Map<String, SecurityScheme> securitySchemeMap = new HashMap<>();
        securitySchemeMap.put(AUTH_TYPE, scheme);

        components.setSecuritySchemes(securitySchemeMap);

        return components;

    }
}
