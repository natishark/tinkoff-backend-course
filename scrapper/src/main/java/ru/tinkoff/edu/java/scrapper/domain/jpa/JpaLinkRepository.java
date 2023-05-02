package ru.tinkoff.edu.java.scrapper.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByUrl(String url);
    List<LinkEntity> findByLastCheckedAtBefore(Timestamp lastCheckedAt);
}
