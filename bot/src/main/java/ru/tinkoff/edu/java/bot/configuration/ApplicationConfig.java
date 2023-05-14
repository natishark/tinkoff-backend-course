package ru.tinkoff.edu.java.bot.configuration;

import com.natishark.course.tinkoff.bot.config.RabbitMQInfo;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.bot.bot.TgBotCredentials;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull String test,
        @NotNull TgBotCredentials tgCredentials,
        @NotNull RabbitMQInfo rabbitMQInfo
) {
}
