package ru.tinkoff.edu.java.bot.botapi;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.edu.java.bot.service.botapi.UserMessageProcessor;

import java.util.random.RandomGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserMessageProcessorTest extends BotApiTest {

    @Autowired
    UserMessageProcessor userMessageProcessor;

    @Test
    public void process_UpdateWithUnknownCommandText_SendMessageContainsUnknownCommandMessage() {
        long chatId = RandomGenerator.getDefault().nextLong();
        Update update = TestUtils.initUpdate(chatId, "/_some-invalid-command");

        SendMessage result = userMessageProcessor.process(update);

        assertThat(userMessageProcessor.commands().size(), equalTo(5));
        assertValidSendMessage(
                result,
                chatId,
                """
                        I don't understand you :c.
                        Use /help command to see the list of available commands"""
        );
    }
}
