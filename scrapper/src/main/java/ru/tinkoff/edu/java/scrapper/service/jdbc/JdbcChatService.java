package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatDao;
import ru.tinkoff.edu.java.scrapper.dto.domain.Chat;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.util.Optional;

@RequiredArgsConstructor
public class JdbcChatService implements ChatService {

    private final JdbcChatDao chatDao;

    @Override
    public void register(long tgChatId) {
        chatDao.add(new Chat(tgChatId));
    }

    @Override
    public void unregister(long tgChatId) {
        chatDao.remove(tgChatId);
    }

    @Override
    public Optional<Chat> findById(long id) {
        return chatDao.findById(id);
    }
}
