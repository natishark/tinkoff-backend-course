package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JdbcChatDaoTest extends IntegrationEnvironment {

    @Autowired
    private JdbcChatDao chatDao;

    @Transactional
    @Rollback
    @Test
    void add_addNewChat() {
        Chat chat = new Chat(12345L);

        boolean added = chatDao.add(chat);

        assertTrue(added);
        List<Chat> chats = chatDao.findAll();
        assertEquals(1, chats.size());
        assertEquals(chat, chats.get(0));
    }

    @Transactional
    @Rollback
    @Test
    void add_addChatTwice() {
        Chat chat = new Chat(1234567L);

        boolean added1 = chatDao.add(chat);
        boolean added2 = chatDao.add(chat);

        assertTrue(added1);
        assertFalse(added2);
        List<Chat> chats = chatDao.findAll();
        assertEquals(1, chats.size());
        assertEquals(chat, chats.get(0));
    }

    @Transactional
    @Rollback
    @Test
    void add_addManyChats() {
        List<Chat> chats = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            chats.add(new Chat(ThreadLocalRandom.current().nextLong()));
        }

        chats.forEach(chatDao::add);

        List<Chat> chatsReturned = chatDao.findAll();
        assertEquals(chats.size(), chatsReturned.size());
    }
}
