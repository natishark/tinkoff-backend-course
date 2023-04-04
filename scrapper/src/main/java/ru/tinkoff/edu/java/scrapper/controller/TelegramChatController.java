package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.natishark.course.tinkoff.bot.exception.ResourceNotFoundException;

@RestController
public class TelegramChatController {

    @PostMapping("api/tg-chat/{id}")
    public void registerChat(@PathVariable Long id) {
    }

    @DeleteMapping("api/tg-chat/{id}")
    public void deleteChat(@PathVariable long id) {
        throw new ResourceNotFoundException("Telegram chat with id " + id + " does not exist");
    }
}
