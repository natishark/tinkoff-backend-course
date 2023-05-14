package ru.tinkoff.edu.java.scrapper.service.messaging;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;

public interface UpdatesNotificationService {
    void send(LinkUpdateRequest updateRequest);
}
