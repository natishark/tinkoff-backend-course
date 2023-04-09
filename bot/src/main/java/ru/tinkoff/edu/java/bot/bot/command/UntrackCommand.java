package ru.tinkoff.edu.java.bot.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.bot.util.ApiUtils;
import ru.tinkoff.edu.java.bot.enums.Command;

@Component
public class UntrackCommand implements TgBotCommand {

    public static String MESSAGE = "Which link do you want to stop tracking?";

    @Override
    public String command() {
        return Command.UNTRACK.name;
    }

    @Override
    public String description() {
        return Command.UNTRACK.description;
    }

    @Override
    public SendMessage handle(Update update) {
        return ApiUtils.createSendMessage(update, MESSAGE)
                .replyMarkup(new ForceReply().inputFieldPlaceholder("Enter the link..."));
    }
}
