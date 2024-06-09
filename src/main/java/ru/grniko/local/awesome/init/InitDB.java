package ru.grniko.local.awesome.init;

import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@Context
public class InitDB {

    private final JdbcTemplate jdbcTemplate;
    private static final List<String> initialSql;

    @Inject
    public InitDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static {
        initialSql = new ArrayList<>();
        initialSql.add("create schema if not exists out_box;");
        initialSql.add("""
                 create table if not exists out_box.handled_message
                (   id            bigint primary key generated by default as identity,
                    topic         varchar(50),
                    partition     numeric,
                    "offset"      numeric,
                    raw_message   varchar(255),
                    status        varchar(2)
                );""");
    }

    @PostConstruct
    public void init() {
        initialSql.forEach(jdbcTemplate::execute);
    }

}
