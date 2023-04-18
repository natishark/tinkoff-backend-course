package ru.tinkoff.edu.java.scrapper.service.jdbc;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.client.bot.BotClient;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Objects;

public class JdbcLinkUpdater implements LinkUpdater {

    private final BotClient botClient;
    private final LinkService linkService;
    private final ChatService chatService;
    private final long checkIndent;
    private final TemporalUnit unit;

    public JdbcLinkUpdater(
            BotClient botClient,
            LinkService linkService,
            ChatService chatService,
            long checkIndent,
            TemporalUnit unit
    ) {
        this.botClient = botClient;
        this.linkService = linkService;
        this.chatService = chatService;
        this.checkIndent = checkIndent;
        this.unit = unit;
    }

    @Override
    public int update() {
        List<Link> updatedLinks = linkService.findAllTrackingLinksLastCheckedBefore(
                        LocalDateTime.now().minus(checkIndent, unit))
                .stream()
                .map(link -> {
                    Link updatedLink = linkService.callApiByUrlAndConvertToLink(URI.create(link.getUrl())).orElse(link);
                    return updatedLink.getUpdatedAt().after(link.getUpdatedAt()) ? updatedLink : null;
                })
                .filter(Objects::nonNull).toList();

        executeUpdate(updatedLinks);

        return updatedLinks.size();
    }

    @Override
    @Transactional
    public void executeUpdate(List<Link> links) {
        linkService.updateLinksSetLastChecked(links, LocalDateTime.now());

        links.forEach(link -> botClient.sendUpdate(new LinkUpdateRequest(
                link.getId(),
                URI.create(link.getUrl()),
                "Link %s was updated".formatted(link.getUrl()),
                chatService.getChatIdsByLinkId(link.getId())
        )).subscribe());
    }
}
