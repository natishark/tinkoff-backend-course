package ru.tinkoff.edu.java.bot.service.botapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.botapi.util.ApiUtils;
import ru.tinkoff.edu.java.bot.service.botapi.util.HtmlMessageBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class HelpCommand implements TgBotCommand {

    private String message() {
        return new HtmlMessageBuilder()
                .addBoldLine("Commands you can use:")
                .addList(Arrays.stream(Command.values())
                        .map(command -> command.representation + " - " + command.description)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public String command() {
        return Command.HELP.name;
    }

    @Override
    public String description() {
        return Command.HELP.description;
    }

    @Override
    public SendMessage handle(Update update) {
        return ApiUtils.createSendMessage(update, message());
    }
}
