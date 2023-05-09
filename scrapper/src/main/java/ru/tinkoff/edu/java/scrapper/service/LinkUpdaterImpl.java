package ru.tinkoff.edu.java.scrapper.service;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.service.messaging.UpdatesNotificationService;
import ru.tinkoff.edu.java.scrapper.util.LinkUpdateEvent;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LinkUpdaterImpl implements ru.tinkoff.edu.java.scrapper.service.LinkUpdater {

    private final UpdatesNotificationService notificationService;
    private final LinkService linkService;
    private final ClientService clientService;
    private final long checkIndent;

    public LinkUpdaterImpl(
            UpdatesNotificationService notificationService,
            LinkService linkService,
            ClientService clientService,
            ApplicationConfig config
    ) {
        this.notificationService = notificationService;
        this.linkService = linkService;
        this.clientService = clientService;
        this.checkIndent = config.scheduler().checkIndent().toMillis();
    }

    @Override
    public int update() {
        List<LinkUpdateEvent> events = linkService.findAllTrackingLinksLastCheckedBefore(
                        LocalDateTime.now().minus(checkIndent, ChronoUnit.MILLIS))
                .stream()
                .map(link -> {
                    LinkUpdateEvent event = new LinkUpdateEvent(
                            link,
                            clientService.getLinkInformation(URI.create(link.getUrl()))
                                    .orElse(link).setId(link.getId())
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

        events.forEach(event -> notificationService.send(new LinkUpdateRequest(
                event.getLink().getId(),
                URI.create(event.getLink().getUrl()),
                event.getDescription(),
                linkService.getChatIdsByLinkId(event.getLink().getId())
        )));
    }
}
