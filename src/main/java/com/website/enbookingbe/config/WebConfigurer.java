package com.website.enbookingbe.config;

import com.website.enbookingbe.quiz.QuizService;
import com.website.enbookingbe.resolver.QuizParamResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import static java.net.URLDecoder.decode;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory>, WebMvcConfigurer {
    private final Environment env;
    private final QuizService quizService;

    @Override
    public void onStartup(ServletContext servletContext) {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        log.info("Web application fully configured");
    }

    @Override
    public void customize(WebServerFactory server) {
        setLocationForStaticAssets(server);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new QuizParamResolver(quizService));
    }

    private void setLocationForStaticAssets(WebServerFactory server) {
        if (server instanceof final ConfigurableServletWebServerFactory servletWebServer) {

            final String prefixPath = resolvePathPrefix();
            final File root = new File(prefixPath + "build/resources/main/static/");

            if (root.exists() && root.isDirectory()) {
                servletWebServer.setDocumentRoot(root);
            }
        }
    }

    private String resolvePathPrefix() {
        final String fullExecutablePath = decode(this.getClass().getResource("").getPath(), StandardCharsets.UTF_8);
        final String rootPath = Paths.get(".").toUri().normalize().getPath();
        final String extractedPath = fullExecutablePath.replace(rootPath, "");
        final int extractionEndIndex = extractedPath.indexOf("build/");

        if (extractionEndIndex <= 0) {
            return Strings.EMPTY;
        }

        return extractedPath.substring(0, extractionEndIndex);
    }
}
