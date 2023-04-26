package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.dto.domain.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JdbcChatDaoTest extends IntegrationEnvironment {

    @Autowired
    private JdbcChatDao chatDao;

    @Transactional
    @Rollback
    @Test
    public void add_addNewChat() {
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
    public void add_addChatTwice() {
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
    public void add_addManyChats() {
        List<Chat> chats = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            chats.add(new Chat(ThreadLocalRandom.current().nextLong()));
        }

        chats.forEach(chatDao::add);

        List<Chat> chatsReturned = chatDao.findAll();
        assertEquals(chats.size(), chatsReturned.size());
    }

    @Transactional
    @Rollback
    @Test
    public void remove_removeNonExistentChat_NoExceptions() {
        assertDoesNotThrow(() -> chatDao.remove(5621L));
    }

    @Transactional
    @Rollback
    @Test
    public void remove_removeExistentChat() {
        var chat = new Chat(87245874L);
        chatDao.add(chat);

        chatDao.remove(chat.id());

        assertTrue(chatDao.findAll().isEmpty());
    }

    @Transactional
    @Rollback
    @Test
    public void findById_noChatWithThatId_EmptyOptional() {
        assertTrue(chatDao.findAll().isEmpty());

        Optional<Chat> chatOptional = chatDao.findById(23457346L);

        assertTrue(chatOptional.isEmpty());
    }

    @Transactional
    @Rollback
    @Test
    public void findById_existChatWithThatId_NotEmptyOptionalEqualsToStoredChat() {
        Chat chat1 = new Chat(64456L);
        chatDao.add(chat1);
        Chat chat2 = new Chat(22L);
        chatDao.add(chat2);

        Optional<Chat> chatOptional1 = chatDao.findById(chat1.id());
        Optional<Chat> chatOptional2 = chatDao.findById(chat2.id());

        assertTrue(chatOptional1.isPresent());
        assertTrue(chatOptional2.isPresent());
        assertEquals(chat1, chatOptional1.get());
        assertEquals(chat2, chatOptional2.get());
    }

    @Transactional
    @Rollback
    @Test
    public void findAll_emptyTable_noChats() {
        List<Chat> chats = chatDao.findAll();

        assertTrue(chats.isEmpty());
    }

    @Transactional
    @Rollback
    @Test
    public void findAll_notEmptyTable_allChatsReturned() {
        List<Chat> initChats = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            initChats.add(new Chat(ThreadLocalRandom.current().nextLong()));
            chatDao.add(initChats.get(initChats.size() - 1));
        }

        List<Chat> chats = chatDao.findAll();


        assertEquals(initChats.size(), chats.size());
        initChats.forEach(chat ->
                assertTrue(chats.contains(chat))
        );
    }
}
