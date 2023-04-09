package ru.tinkoff.edu.java.bot.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.bot.command.ExecutableCommand;
import ru.tinkoff.edu.java.bot.bot.command.TgBotCommand;
import ru.tinkoff.edu.java.bot.bot.util.ApiUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMessageProcessor {

    private static final String UNKNOWN_COMMAND_MESSAGE = """
                                I don't understand you :c.
                                Use /help command to see the list of available commands""";

    private final List<ExecutableCommand> commands;

    @Autowired
    public UserMessageProcessor(List<ExecutableCommand> commands) {
        this.commands = commands;
    }

    public List<TgBotCommand> commands() {
        return commands.stream()
                .filter(c -> c instanceof TgBotCommand)
                .map(c -> (TgBotCommand) c)
                .collect(Collectors.toList());
    }

    public SendMessage process(Update update) {
        return commands.stream()
                .filter(c -> c.supports(update))
                .findFirst()
                .map(c -> c.handle(update))
                .orElse(ApiUtils.createSendMessage(update, UNKNOWN_COMMAND_MESSAGE));
    }
}
