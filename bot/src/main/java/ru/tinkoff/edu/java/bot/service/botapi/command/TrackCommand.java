package ru.tinkoff.edu.java.bot.service.botapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.botapi.util.ApiUtils;

@Component
public class TrackCommand implements TgBotCommand {

    public static String MESSAGE = "Which link do you want to track?";

    @Override
    public String command() {
        return Command.TRACK.name;
    }

    @Override
    public String description() {
        return Command.TRACK.description;
    }

    @Override
    public SendMessage handle(Update update) {
        return ApiUtils.createSendMessage(update, MESSAGE)
                .replyMarkup(new ForceReply().inputFieldPlaceholder("Enter the link..."));
    }
}
