package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.dto.domain.Chat;

import java.util.Optional;

public interface ChatService {
    void register(long tgChatId);
    void unregister(long tgChatId);
    Optional<Chat> findById(long id);
}
