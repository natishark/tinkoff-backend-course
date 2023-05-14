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

    private static final String DLQ_SUFFIX = ".dlq";

    private final ApplicationConfig config;

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(config.rabbitMQInfo().updateQueueName())
                .deadLetterExchange(config.rabbitMQInfo().exchangeName())
                .deadLetterRoutingKey(config.rabbitMQInfo().updatesRoutingKey() + DLQ_SUFFIX)
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
