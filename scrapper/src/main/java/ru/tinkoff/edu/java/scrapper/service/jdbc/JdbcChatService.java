package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatDao;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatLinkDao;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JdbcChatService implements ChatService {

    private final JdbcChatDao chatDao;
    private final JdbcChatLinkDao chatLinkDao;

    public JdbcChatService(JdbcChatDao chatDao, JdbcChatLinkDao chatLinkDao) {
        this.chatDao = chatDao;
        this.chatLinkDao = chatLinkDao;
    }

    @Override
    public void register(long tgChatId) {
        chatDao.add(new Chat(tgChatId));
    }

    @Override
    public void unregister(long tgChatId) {
        chatDao.remove(tgChatId);
    }

    @Override
    public List<Long> getChatIdsByLinkId(long linkId) {
        return chatLinkDao.findAllChatsByLinkId(linkId).stream()
                .map(Chat::id).collect(Collectors.toList());
    }

    @Override
    public Optional<Chat> findById(long id) {
        return chatDao.findById(id);
    }
}
