package ru.tinkoff.edu.java.bot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final ApplicationConfig config;
    private final String DLQ_ROUTING_KEY = config.rabbitMQInfo().updatesRoutingKey() + ".dlq";
    private final String DLQ_NAME = config.rabbitMQInfo().updateQueueName() + ".dlq";

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
    public Queue deadLetterQueue() {
        return new Queue(DLQ_NAME);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(directExchange())
                .with(DLQ_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
