package ru.tinkoff.edu.java.scrapper.service.jdbc;

import com.natishark.course.tinkoff.bot.exception.ResourceNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import parser.LinkParser;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.domain.ChatLinkDto;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatLinkDao;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.service.AbstractLinkService;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JdbcLinkService extends AbstractLinkService {

    private final JdbcChatLinkDao chatLinkDao;
    private final ChatService chatService;
    private final JdbcLinkDao linkDao;

    public JdbcLinkService(
            JdbcChatLinkDao chatLinkDao,
            ChatService chatService,
            JdbcLinkDao linkDao,
            LinkParser linkParser,
            GitHubClient gitHubClient,
            StackOverflowClient stackOverflowClient,
            ConversionService conversionService
    ) {
        super(linkParser, gitHubClient, stackOverflowClient, conversionService);
        this.chatLinkDao = chatLinkDao;
        this.chatService = chatService;
        this.linkDao = linkDao;
    }

    @Override
    public Link add(long tgChatId, URI url) {
        validateChat(tgChatId);

        return chatLinkDao.subscribe(
                tgChatId,
                callApiByUrlAndConvertToLink(url)
                        .orElseThrow(() -> new ResourceNotFoundException("No source on this link found."))
        );
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        validateChat(tgChatId);

        String validatedUrl = callApiByUrlAndConvertToLink(url)
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
    public List<Link> findAllTrackingLinksLastCheckedBefore(LocalDateTime lastCheckTime) {
        return linkDao.findAllTrackingLinksLastCheckedBefore(Timestamp.valueOf(lastCheckTime));
    }

    public void updateLinksSetLastChecked(List<Link> links, LocalDateTime lastCheckTime) {
        linkDao.batchUpdate(links, Timestamp.valueOf(lastCheckTime));
    }

    private void validateChat(long chatId) {
        if (chatService.findById(chatId).isEmpty()) {
            throw new ResourceNotFoundException("Chat with id '%d' does not exist".formatted(chatId));
        }
    }
}
