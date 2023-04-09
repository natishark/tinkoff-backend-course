package ru.tinkoff.edu.java.bot.bot.command;

import com.natishark.course.tinkoff.bot.dto.LinkResponse;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.bot.util.ApiUtils;
import ru.tinkoff.edu.java.bot.service.ScrapperService;

import java.util.Optional;

@Component
public class UntrackContinueCommand implements ExecutableCommand {

    private static final String STOPPED_MESSAGE = "Tracking stopped.";
    private static final String NOT_FOUND_MESSAGE = "Okay. I haven't tracked this link anyway)))";

    private final ScrapperService scrapperService;

    public UntrackContinueCommand(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @Override
    public SendMessage handle(Update update) {
        Optional<LinkResponse> linkResponse = scrapperService.removeLinkTracking(
                ApiUtils.getChatId(update),
                update.message().text()
        );

        return ApiUtils.createSendMessage(update, linkResponse.isPresent() ? STOPPED_MESSAGE : NOT_FOUND_MESSAGE);
    }

    @Override
    public boolean supports(Update update) {
        return ApiUtils.isPreviousMessageTextSatisfy(update, t -> t.equals(UntrackCommand.MESSAGE));
    }
}
