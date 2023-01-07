package com.website.enbookingbe.core.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Slf4j
@Configuration
public class WebCorsConfiguration {
    private static final List<String> CORS_PATTERNS = List.of("/api/**", "/management/**");

    @Bean
    @ConfigurationProperties(prefix = "website.cors")
    public CorsConfiguration corsConfiguration() {
        return new CorsConfiguration();
    }

    @Bean
    public CorsFilter corsFilter(CorsConfiguration cors) {
        log.debug("Registering CORS filter");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CORS_PATTERNS.forEach(pattern -> source.registerCorsConfiguration(pattern, cors));

        return new CorsFilter(source);
    }
}
