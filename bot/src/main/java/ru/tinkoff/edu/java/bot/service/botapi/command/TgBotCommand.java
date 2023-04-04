package ru.tinkoff.edu.java.bot.service.botapi.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import ru.tinkoff.edu.java.bot.service.botapi.util.ApiUtils;

public interface TgBotCommand extends ExecutableCommand {

    String command();
    String description();

    default BotCommand toBotCommand() {
        return new BotCommand(command(), description());
    }

    @Override
    default boolean supports(Update update) {
        return ApiUtils.isMessageTextSatisfy(
                update,
                t -> t.equals("/" + command())
        );
    }
}
