package ru.tinkoff.edu.java.bot.service.botapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.botapi.util.ApiUtils;
import ru.tinkoff.edu.java.bot.service.botapi.util.HtmlMessageBuilder;
import ru.tinkoff.edu.java.bot.service.scrapperclient.ScrapperClient;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ListCommand implements TgBotCommand {

    private static final String NO_LINKS_MESSAGE = "You don't have any tracking links.";

    private final ScrapperClient scrapperClient;

    public ListCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
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
        final var links = Objects.requireNonNull(scrapperClient
                        .getTrackedLinks(ApiUtils.getChatId(update)).block())
                .links()
                .stream().map(linkResponse -> linkResponse.url().toString())
                .collect(Collectors.toList());

        return ApiUtils.createSendMessage(
                update,
                links.isEmpty() ? NO_LINKS_MESSAGE : new HtmlMessageBuilder()
                        .addBoldLine("Your tracked links:")
                        .addList(links)
                        .build()
        );
    }
}
