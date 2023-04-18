package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.scrapper.client.bot.BotClient;
import ru.tinkoff.edu.java.scrapper.scheduler.LinkUpdaterScheduler;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkUpdater;

import java.time.temporal.ChronoUnit;

@EnableScheduling
@Configuration
public class SchedulerConfiguration {

    @Bean
    public LinkUpdaterScheduler linkUpdaterScheduler(LinkUpdater linkUpdater) {
        return new LinkUpdaterScheduler(linkUpdater);
    }

    @Bean
    public long schedulerIntervalMs(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }

    @Bean
    public LinkUpdater linkUpdater(ApplicationConfig config, BotClient botClient, LinkService linkService, ChatService chatService) {
        return new JdbcLinkUpdater(
                botClient,
                linkService,
                chatService,
                config.scheduler().checkIndent().toMillis(),
                ChronoUnit.MILLIS
        );
    }
}
