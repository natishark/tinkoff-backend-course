package ru.tinkoff.edu.java.scrapper.service.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.dto.domain.Chat;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JpaChatServiceTest extends IntegrationEnvironment {

    @Autowired
    private JpaChatService chatService;

    @Transactional
    @Rollback
    @Test
    public void register_registerOneChat_chatRegistered() {
        Long chatId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);

        chatService.register(chatId);

        Optional<Chat> chat = chatService.findById(chatId);
        assertTrue(chat.isPresent());
        assertEquals(chatId, chat.get().id());
    }

    @Transactional
    @Rollback
    @Test
    public void register_registerOneChatTwice_chatRegisteredOnceAndNoExceptions() {
        long chatId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);

        chatService.register(chatId);
        assertDoesNotThrow(() -> chatService.register(chatId));
    }

    @Transactional
    @Rollback
    @Test
    public void unregister_deleteRegisteredChat_noChatFoundById() {
        long chatId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        chatService.register(chatId);

        chatService.unregister(chatId);

        assertTrue(chatService.findById(chatId).isEmpty());
    }

    @Transactional
    @Rollback
    @Test
    public void unregister_deleteNotRegisteredChat_noChatFoundById() {
        long chatId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);

        assertDoesNotThrow(() -> chatService.unregister(chatId));
    }

    @Transactional
    @Rollback
    @Test
    public void findById_noChatWithId_emptyOptional() {
        Optional<Chat> chat = chatService.findById(786);

        assertTrue(chat.isEmpty());
    }

    @Transactional
    @Rollback
    @Test
    public void findById_existChatWithId_emptyOptional() {
        long chatId = 69764L;
        chatService.register(chatId);

        Optional<Chat> chat = chatService.findById(chatId);

        assertTrue(chat.isPresent());
    }
}
