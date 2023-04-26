package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.scrapper.scheduler.LinkUpdaterScheduler;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

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
}
