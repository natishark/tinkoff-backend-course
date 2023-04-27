package ru.tinkoff.edu.java.scrapper.service.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.dto.domain.Chat;

public class ChatEntityToChatConverter implements Converter<ChatEntity, Chat> {

    @Override
    public Chat convert(@NotNull ChatEntity entity) {
        return new Chat(entity.getId());
    }
}
