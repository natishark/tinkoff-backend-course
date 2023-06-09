package ru.tinkoff.edu.java.bot.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface ExecutableCommand {

    SendMessage handle(Update update);
    boolean supports(Update update);
}
