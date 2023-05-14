package ru.tinkoff.edu.java.scrapper.service.messaging;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.service.ScrapperQueueProducer;

@RequiredArgsConstructor
public class RabbitMqUpdatesNotificationService implements UpdatesNotificationService {

    private final ScrapperQueueProducer producer;

    @Override
    public void send(LinkUpdateRequest updateRequest) {
        producer.send(updateRequest);
    }
}
