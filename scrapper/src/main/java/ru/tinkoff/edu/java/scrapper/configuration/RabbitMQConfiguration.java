package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitMQConfiguration {

    private final ApplicationConfig config;
    private final String DLQ_ROUTING_KEY = config.rabbitMQInfo().updatesRoutingKey() + ".dlq";

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(config.rabbitMQInfo().updateQueueName())
                .deadLetterExchange(config.rabbitMQInfo().exchangeName())
                .deadLetterRoutingKey(DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(config.rabbitMQInfo().exchangeName());
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(directExchange())
                .with(config.rabbitMQInfo().updatesRoutingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
