package ru.tinkoff.edu.java.scrapper.service.jdbc;

import com.natishark.course.tinkoff.bot.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.controller.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatLinkDao;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.dto.domain.Chat;
import ru.tinkoff.edu.java.scrapper.dto.domain.ChatLinkDto;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.ClientService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final JdbcChatLinkDao chatLinkDao;
    private final ChatService chatService;
    private final JdbcLinkDao linkDao;
    private final ClientService clientService;

    @Override
    public Link add(long tgChatId, URI url) {
        validateChat(tgChatId);

        return chatLinkDao.subscribe(
                tgChatId,
                clientService.getLinkInformation(url)
                        .orElseThrow(() -> new ResourceNotFoundException("No source on this link found."))
        );
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        validateChat(tgChatId);

        String validatedUrl = clientService.getLinkInformation(url)
                .orElseThrow(() -> new ResourceNotFoundException("No source on this link found."))
                .getUrl();

        Link link = linkDao.findByUrl(validatedUrl).orElseThrow(ResourceNotFoundException::new);

        chatLinkDao.unsubscribe(new ChatLinkDto(
                tgChatId,
                link.getId()
        ));
        return link;
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        validateChat(tgChatId);

        return chatLinkDao.findAllLinksByChatId(tgChatId);
    }

    @Override
    public List<Long> getChatIdsByLinkId(long linkId) {
        return chatLinkDao.findAllChatsByLinkId(linkId).stream()
                .map(Chat::id).collect(Collectors.toList());
    }

    @Override
    public List<Link> findAllTrackingLinksLastCheckedBefore(LocalDateTime lastCheckTime) {
        return linkDao.findAllTrackingLinksLastCheckedBefore(Timestamp.valueOf(lastCheckTime));
    }

    public void updateLinksSetLastChecked(List<Link> links, LocalDateTime lastCheckTime) {
        linkDao.batchUpdate(links, Timestamp.valueOf(lastCheckTime));
    }

    private void validateChat(long chatId) {
        if (chatService.findById(chatId).isEmpty()) {
            throw new ChatNotFoundException(chatId);
        }
    }
}
