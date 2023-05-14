package ru.tinkoff.edu.java.bot.service;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = "${app.rabbit-mq-info.update-queue-name}")
@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapperQueueListener {

    private final UpdateService updateService;

    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {
        log.info("Received link update: {}", update);
        updateService.handleUpdate(update);
    }
}
