package ru.tinkoff.edu.java.scrapper.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.ChatEntity;

public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {
}
