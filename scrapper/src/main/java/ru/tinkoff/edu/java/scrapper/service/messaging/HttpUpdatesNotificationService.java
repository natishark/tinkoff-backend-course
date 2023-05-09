package ru.tinkoff.edu.java.scrapper.service.messaging;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.client.bot.BotClient;

@RequiredArgsConstructor
public class HttpUpdatesNotificationService implements UpdatesNotificationService {

    private final BotClient botClient;

    @Override
    public void send(LinkUpdateRequest updateRequest) {
        botClient.sendUpdate(updateRequest).subscribe();
    }
}
