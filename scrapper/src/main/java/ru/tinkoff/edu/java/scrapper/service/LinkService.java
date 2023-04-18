package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkService {
    Link add(long tgChatId, URI url);
    Link remove(long tgChatId, URI url);
    List<Link> listAll(long tgChatId);
    List<Link> findAllTrackingLinksLastCheckedBefore(LocalDateTime lastCheckTime);
    Optional<Link> callApiByUrlAndConvertToLink(URI url);
    void updateLinksSetLastChecked(List<Link> links, LocalDateTime lastCheckTime);
}
