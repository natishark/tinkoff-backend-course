package ru.tinkoff.edu.java.bot.botapi;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BotApiTest {

    protected void assertValidSendMessage(SendMessage result, long chatId, String text) {
        assertThat(result.getParameters(), notNullValue());
        assertTrue(result.getParameters().containsKey("chat_id"));
        assertThat(result.getParameters().get("chat_id"), equalTo(chatId));
        assertTrue(result.getParameters().containsKey("text"));
        assertThat(result.getParameters().get("text"), equalTo(text));
        assertTrue(result.getParameters().containsKey("parse_mode"));
        assertThat(result.getParameters().get("parse_mode"), equalTo(ParseMode.HTML.name()));
    }
}
