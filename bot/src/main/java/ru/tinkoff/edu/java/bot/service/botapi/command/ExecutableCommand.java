package ru.tinkoff.edu.java.bot.service.botapi.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface ExecutableCommand {

    SendMessage handle(Update update);
    boolean supports(Update update);
}
