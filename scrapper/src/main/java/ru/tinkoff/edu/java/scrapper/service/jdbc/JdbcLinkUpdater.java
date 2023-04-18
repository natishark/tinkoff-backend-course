package ru.tinkoff.edu.java.scrapper.service.jdbc;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.client.bot.BotClient;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.util.LinkUpdateEvent;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        List<LinkUpdateEvent> events = linkService.findAllTrackingLinksLastCheckedBefore(
                        LocalDateTime.now().minus(checkIndent, unit))
                .stream()
                .map(link -> {
                    LinkUpdateEvent event = new LinkUpdateEvent(
                            link,
                            linkService.callApiByUrlAndConvertToLink(
                                    URI.create(link.getUrl())).orElse(link).setId(link.getId()
                            )
                    );
                    return event.wasUpdated() ? event : null;
                })
                .filter(Objects::nonNull).toList();

        executeUpdate(events);

        return events.size();
    }

    @Override
    @Transactional
    public void executeUpdate(List<LinkUpdateEvent> events) {
        linkService.updateLinksSetLastChecked(
                events.stream().map(LinkUpdateEvent::getLink).collect(Collectors.toList()),
                LocalDateTime.now()
        );

        events.forEach(event -> botClient.sendUpdate(new LinkUpdateRequest(
                event.getLink().getId(),
                URI.create(event.getLink().getUrl()),
                event.getDescription(),
                chatService.getChatIdsByLinkId(event.getLink().getId())
        )).subscribe());
    }
}
