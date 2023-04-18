package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@RestController
public class TelegramChatController {

    private final ChatService chatService;

    public TelegramChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("api/tg-chat/{id}")
    public void registerChat(@PathVariable Long id) {
        chatService.register(id);
    }

    @DeleteMapping("api/tg-chat/{id}")
    public void deleteChat(@PathVariable long id) {
        chatService.unregister(id);
    }
}
