package ru.tinkoff.edu.java.bot.service.botapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.botapi.util.ApiUtils;
import ru.tinkoff.edu.java.bot.service.scrapperclient.ScrapperClient;

@Component
public class StartCommand implements TgBotCommand {

    private static final String GREETING = """
            <b>Greetings, dear friend.</b>
            
            I'll help you track the changes from the questions on <u>StackOverflow</u> and the repositories on <u>GitHub</u>.
            
            For more information, use /help command.""";

    private final ScrapperClient scrapperClient;

    @Autowired
    public StartCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String command() {
        return Command.START.name;
    }

    @Override
    public String description() {
        return Command.START.description;
    }

    @Override
    public SendMessage handle(Update update) {
        scrapperClient.registerChat(ApiUtils.getChatId(update)).block();

        return ApiUtils.createSendMessage(update, GREETING);
    }
}
