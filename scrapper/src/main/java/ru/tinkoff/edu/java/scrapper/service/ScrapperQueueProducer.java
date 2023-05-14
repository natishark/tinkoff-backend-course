package ru.tinkoff.edu.java.scrapper.service;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;

public class ScrapperQueueProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationConfig config;

    public ScrapperQueueProducer(RabbitTemplate rabbitTemplate, ApplicationConfig config) {
        this.rabbitTemplate = rabbitTemplate;
        this.config = config;
        rabbitTemplate.setExchange(config.rabbitMQInfo().exchangeName());
    }

    public void send(LinkUpdateRequest update) {
        rabbitTemplate.convertAndSend(config.rabbitMQInfo().updatesRoutingKey(), update);
    }
}
