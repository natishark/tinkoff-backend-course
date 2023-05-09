package ru.tinkoff.edu.java.scrapper.configuration.messaging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.bot.BotClient;
import ru.tinkoff.edu.java.scrapper.service.messaging.HttpUpdatesNotificationService;
import ru.tinkoff.edu.java.scrapper.service.messaging.UpdatesNotificationService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpMessagingConfiguration {

    @Bean
    public UpdatesNotificationService updatesNotificationService(BotClient client) {
        return new HttpUpdatesNotificationService(client);
    }

    @Bean
    public BotClient botClient() {
        return new BotClient();
    }
}
