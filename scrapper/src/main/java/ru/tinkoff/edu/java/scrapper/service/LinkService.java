package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.dto.domain.Link;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public interface LinkService {
    Link add(long tgChatId, URI url);
    Link remove(long tgChatId, URI url);
    List<Link> listAll(long tgChatId);
    List<Long> getChatIdsByLinkId(long linkId);
    List<Link> findAllTrackingLinksLastCheckedBefore(LocalDateTime lastCheckTime);
    void updateLinksSetLastChecked(List<Link> links, LocalDateTime lastCheckTime);
}
