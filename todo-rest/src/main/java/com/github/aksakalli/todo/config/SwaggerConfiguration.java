package com.github.aksakalli.todo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
@Profile("!" + Constants.SPRING_PROFILE_PRODUCTION)
public class SwaggerConfiguration implements EnvironmentAware {


    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "swagger.");
    }


    /**
     * Swagger Springfox configuration.
     */
    @Bean
    public Docket swaggerSpringfoxDocket() {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .securitySchemes(newArrayList(new BasicAuth("test")))
            .genericModelSubstitutes(ResponseEntity.class)
            .forCodeGeneration(true)
            .genericModelSubstitutes(ResponseEntity.class)
            .directModelSubstitute(org.joda.time.LocalDate.class, String.class)
            .directModelSubstitute(org.joda.time.LocalDateTime.class, Date.class)
            .directModelSubstitute(org.joda.time.DateTime.class, Date.class)
            .directModelSubstitute(java.time.LocalDate.class, String.class)
            .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
            .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
            .select()
            .paths(regex(DEFAULT_INCLUDE_PATTERN))
            .build();
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    /**
     * API Info as it appears on the swagger-ui page.
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
            propertyResolver.getProperty("title"),
            propertyResolver.getProperty("description"),
            propertyResolver.getProperty("version"),
            propertyResolver.getProperty("termsOfServiceUrl"),
            propertyResolver.getProperty("contact"),
            propertyResolver.getProperty("license"),
            propertyResolver.getProperty("licenseUrl"));
    }
}
