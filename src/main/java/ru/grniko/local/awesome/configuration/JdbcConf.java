package ru.grniko.local.awesome.configuration;

import io.micronaut.context.ApplicationContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Singleton
public class JdbcConf {

    @Inject
    ApplicationContext context;

    @Singleton
    public JdbcTemplate getJdbcTemplate() {

        return new JdbcTemplate(context.getBean(DataSource.class));
    }

}
