package ru.tinkoff.edu.java.scrapper.configuration.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.service.ScrapperQueueProducer;
import ru.tinkoff.edu.java.scrapper.service.messaging.RabbitMqUpdatesNotificationService;
import ru.tinkoff.edu.java.scrapper.service.messaging.UpdatesNotificationService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitMqMessagingConfiguration {

    @Bean
    public UpdatesNotificationService updatesNotificationService(ScrapperQueueProducer producer) {
        return new RabbitMqUpdatesNotificationService(producer);
    }

    @Bean
    public ScrapperQueueProducer scrapperQueueProducer(
            RabbitTemplate rabbitTemplate,
            ApplicationConfig config
    ) {
        return new ScrapperQueueProducer(rabbitTemplate, config);
    }
}
