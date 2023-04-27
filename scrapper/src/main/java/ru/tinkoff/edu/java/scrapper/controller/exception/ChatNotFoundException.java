package ru.tinkoff.edu.java.scrapper.controller.exception;

import com.natishark.course.tinkoff.bot.exception.ResourceNotFoundException;

public class ChatNotFoundException extends ResourceNotFoundException {
    public ChatNotFoundException(Long chatId) {
        super("Chat with id '%d' does not exist".formatted(chatId));
    }
}
