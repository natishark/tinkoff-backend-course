package ru.tinkoff.edu.java.scrapper.configuration;

import com.natishark.course.tinkoff.bot.config.RabbitMQInfo;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull String test,
        @NotNull Scheduler scheduler,
        @NotNull AccessType databaseAccessType,
        @NotNull RabbitMQInfo rabbitMQInfo,
        @NotNull Boolean useQueue
) {

    public record Scheduler(Duration interval, Duration checkIndent) {
    }

    public enum AccessType {
        JDBC, JPA
    }
}
