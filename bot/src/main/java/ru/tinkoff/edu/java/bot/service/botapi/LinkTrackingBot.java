package ru.tinkoff.edu.java.bot.service.botapi;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.botapi.command.TgBotCommand;

import java.util.List;

@Component
public class LinkTrackingBot implements AutoCloseable, UpdatesListener {

    private final TelegramBot telegramBot;
    private final UserMessageProcessor processor;

    @Autowired
    public LinkTrackingBot(UserMessageProcessor processor, TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        this.processor = processor;

        execute(new SetMyCommands(
                processor.commands()
                        .stream()
                        .map(TgBotCommand::toBotCommand)
                        .toArray(BotCommand[]::new)
        ));

        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.message() != null) {
                execute(processor.process(update));
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        execute(new DeleteMyCommands());
        telegramBot.shutdown();
    }

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }
}
