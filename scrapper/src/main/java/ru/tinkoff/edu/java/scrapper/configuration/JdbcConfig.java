package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public record JdbcConfig(DataSource dataSource) {

    @Bean
    public DataSource postgresqlDataSource() {
        return dataSource;
    }
}
