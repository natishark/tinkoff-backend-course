package ru.tinkoff.edu.java.bot.bot.util;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.Optional;
import java.util.function.Predicate;

public class ApiUtils {

    public static long getChatId(Update update) {
        return update.message().chat().id();
    }

    private static Optional<Message> getMessage(Update update) {
        return Optional.ofNullable(update)
                .map(Update::message);
    }

    public static boolean isMessageTextSatisfy(Update update, Predicate<String> predicate) {
        return getMessage(update)
                .map(Message::text)
                .filter(predicate)
                .isPresent();
    }

    public static boolean isPreviousMessageTextSatisfy(Update update, Predicate<String> predicate) {
        return getMessage(update)
                .map(Message::replyToMessage)
                .map(Message::text)
                .filter(predicate)
                .isPresent();
    }

    public static SendMessage createSendMessage(Update update, String text) {
        return new SendMessage(getChatId(update), text)
                .parseMode(ParseMode.HTML);
    }
}
