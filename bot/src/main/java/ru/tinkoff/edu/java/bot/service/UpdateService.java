package ru.tinkoff.edu.java.bot.service;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.bot.LinkTrackingBot;

@Service
@RequiredArgsConstructor
public class UpdateService {

    private final LinkTrackingBot bot;

    public void handleUpdate(LinkUpdateRequest linkUpdateRequest) {
        bot.sendMessages(linkUpdateRequest.tgChatIds(), linkUpdateRequest.description());
    }
}
