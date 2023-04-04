package ru.tinkoff.edu.java.bot.service.botapi.command;

import com.natishark.course.tinkoff.bot.dto.LinkResponse;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.botapi.util.ApiUtils;
import ru.tinkoff.edu.java.bot.service.scrapperclient.ScrapperClient;

import java.net.URI;
import java.util.Optional;

@Component
public class TrackContinueCommand implements ExecutableCommand {

    private final ScrapperClient scrapperClient;

    public TrackContinueCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public SendMessage handle(Update update) {
        Optional<LinkResponse> linkResponse = scrapperClient.addLinkTracking(
                ApiUtils.getChatId(update),
                URI.create(update.message().text())
        ).blockOptional();

        return ApiUtils.createSendMessage(update, linkResponse
                .map(response -> "I am tracking the changes by the link " + response.url() + "\uD83D\uDE11")
                .orElse("I'm already tracking this link."));
    }

    @Override
    public boolean supports(Update update) {
        return ApiUtils.isPreviousMessageTextSatisfy(update, t -> t.equals(TrackCommand.MESSAGE));
    }
}
