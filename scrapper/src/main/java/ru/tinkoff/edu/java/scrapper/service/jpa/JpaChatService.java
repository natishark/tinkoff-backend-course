package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.dto.domain.Chat;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {

    private final JpaChatRepository chatRepository;
    private final ConversionService conversionService;

    @Override
    public void register(long tgChatId) {
        chatRepository.save(new ChatEntity(tgChatId));
    }

    @Override
    public void unregister(long tgChatId) {
        if (chatRepository.existsById(tgChatId)) {
            chatRepository.deleteById(tgChatId);
        }
    }

    @Override
    public Optional<Chat> findById(long id) {
        return chatRepository.findById(id)
                .map(e -> conversionService.convert(e, Chat.class));
    }
}
