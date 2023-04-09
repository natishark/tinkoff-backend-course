package ru.tinkoff.edu.java.bot.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.bot.util.ApiUtils;
import ru.tinkoff.edu.java.bot.bot.util.HtmlMessageBuilder;
import ru.tinkoff.edu.java.bot.enums.Command;
import ru.tinkoff.edu.java.bot.service.ScrapperService;

@Component
public class ListCommand implements TgBotCommand {

    private static final String NO_LINKS_MESSAGE = "You don't have any tracking links.";

    private final ScrapperService scrapperService;

    public ListCommand(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @Override
    public String command() {
        return Command.LIST.name;
    }

    @Override
    public String description() {
        return Command.LIST.description;
    }

    @Override
    public SendMessage handle(Update update) {
        final var links = scrapperService.getTrackedLinks(ApiUtils.getChatId(update));

        return ApiUtils.createSendMessage(
                update,
                links.isEmpty() ? NO_LINKS_MESSAGE : new HtmlMessageBuilder()
                        .addBoldLine("Your tracked links:")
                        .addList(links)
                        .build()
        );
    }
}
