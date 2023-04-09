package ru.tinkoff.edu.java.bot.bot.command;

import com.natishark.course.tinkoff.bot.dto.LinkResponse;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.bot.util.ApiUtils;
import ru.tinkoff.edu.java.bot.service.ScrapperService;

import java.util.Optional;

@Component
public class TrackContinueCommand implements ExecutableCommand {

    private final ScrapperService scrapperService;

    public TrackContinueCommand(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @Override
    public SendMessage handle(Update update) {
        Optional<LinkResponse> linkResponse = scrapperService.addLinkTracking(
                ApiUtils.getChatId(update),
                update.message().text()
        );

        return ApiUtils.createSendMessage(update, linkResponse
                .map(response -> "I am tracking the changes by the link " + response.url() + "\uD83D\uDE11")
                .orElse("I'm already tracking this link."));
    }

    @Override
    public boolean supports(Update update) {
        return ApiUtils.isPreviousMessageTextSatisfy(update, t -> t.equals(TrackCommand.MESSAGE));
    }
}
