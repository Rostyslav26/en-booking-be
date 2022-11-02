package com.websitre.enbookingbe.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@Profile(SpringProfiles.PROD)
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {

    @Value("${website.http.cache.timeToLiveInDays}")
    private int timeToLiveInDays;

    private static final String[] RESOURCE_LOCATIONS = new String[]{
        "classpath:/static/",
        "classpath:/static/content/",
        "classpath:/static/i18n/",
    };
    private static final String[] RESOURCE_PATHS = new String[]{
        "/*.js",
        "/*.css",
        "/*.svg",
        "/*.png",
        "*.ico",
        "/content/**",
        "/i18n/*",
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final ResourceHandlerRegistration resourceHandlerRegistration = appendResourceHandler(registry);
        initializeResourceHandler(resourceHandlerRegistration);
    }

    protected ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
        return registry.addResourceHandler(RESOURCE_PATHS);
    }

    protected void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
        resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS).setCacheControl(getCacheControl());
    }

    protected CacheControl getCacheControl() {
        return CacheControl.maxAge(timeToLiveInDays, TimeUnit.DAYS).cachePublic();
    }
}