package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    void register(long tgChatId);
    void unregister(long tgChatId);
    List<Long> getChatIdsByLinkId(long linkId);
    Optional<Chat> findById(long id);
}
