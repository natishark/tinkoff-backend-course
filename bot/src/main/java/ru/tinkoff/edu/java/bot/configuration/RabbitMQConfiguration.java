package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    private static final String DLQ_SUFFIX = ".dlq";

    private final ApplicationConfig config;
    private final String dlqRoutingKey;

    public RabbitMQConfiguration(ApplicationConfig config) {
        this.config = config;
        dlqRoutingKey = config.rabbitMQInfo().updatesRoutingKey() + DLQ_SUFFIX;
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(config.rabbitMQInfo().updateQueueName())
                .deadLetterExchange(config.rabbitMQInfo().exchangeName())
                .deadLetterRoutingKey(dlqRoutingKey)
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
        return new Queue(config.rabbitMQInfo().updateQueueName() + DLQ_SUFFIX);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(directExchange())
                .with(dlqRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
