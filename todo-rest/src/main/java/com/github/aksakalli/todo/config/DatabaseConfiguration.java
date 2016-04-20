package com.github.aksakalli.todo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableJpaRepositories("com.github.aksakalli.todo.repository")
public class DatabaseConfiguration implements EnvironmentAware {

    private RelaxedPropertyResolver dataSourcePropertyResolver;

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private Environment env;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
        this.dataSourcePropertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        log.debug("Configuring Datasource");
        if (dataSourcePropertyResolver.getProperty("url") == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                    " cannot start. Please check your Spring profile, current profiles are: {}",
                Arrays.toString(env.getActiveProfiles()));
            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dataSourcePropertyResolver.getProperty("dataSourceClassName"));
        hikariConfig.setJdbcUrl(dataSourcePropertyResolver.getProperty("url"));
        hikariConfig.setUsername(dataSourcePropertyResolver.getProperty("username"));
        hikariConfig.setPassword(dataSourcePropertyResolver.getProperty("password"));
        return new HikariDataSource(hikariConfig);
    }

}
