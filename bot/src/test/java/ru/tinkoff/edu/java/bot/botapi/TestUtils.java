package ru.tinkoff.edu.java.bot.botapi;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.test.util.ReflectionTestUtils;

public class TestUtils {

    public static Update initUpdate(long chatId) {
        return initUpdate(chatId, "");
    }

    public static Update initUpdate(long chatId, String text) {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();

        ReflectionTestUtils.setField(chat, "id", chatId);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", text);
        ReflectionTestUtils.setField(update, "message", message);

        return update;
    }
}
