package com.natishark.course.tinkoff.bot.config;

public record RabbitMQInfo(
        String updateQueueName,
        String exchangeName,
        String updatesRoutingKey
) {
}
