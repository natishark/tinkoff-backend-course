package ru.tinkoff.edu.java.bot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.bot.LinkTrackingBot;

@RestController
@RequiredArgsConstructor
public class UpdatesController {

    private final LinkTrackingBot bot;

    @PostMapping("api/updates")
    public void sendUpdate(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest) {
        bot.sendMessages(linkUpdateRequest.tgChatIds(), linkUpdateRequest.description());
    }
}
