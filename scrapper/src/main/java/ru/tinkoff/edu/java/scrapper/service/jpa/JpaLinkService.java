package ru.tinkoff.edu.java.scrapper.service.jpa;

import com.natishark.course.tinkoff.bot.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.controller.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.SubscriptionEntity;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;
import ru.tinkoff.edu.java.scrapper.service.ClientService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;
    private final ClientService clientService;
    private final ConversionService conversionService;

    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        ChatEntity chat = chatRepository.findById(tgChatId)
                .orElseThrow(() -> new ChatNotFoundException(tgChatId));

        Link link = clientService.getLinkInformation(url)
                .orElseThrow(() -> new ResourceNotFoundException("No source on this link found."));
        LinkEntity linkEntity = linkRepository.findByUrl(link.getUrl())
                .orElseGet(() -> linkRepository.save(conversionService.convert(link, LinkEntity.class)));

        link.setId(linkEntity.getId());

        if (chat.getChatSubscriptions().stream().noneMatch(e -> e.getLink().getId().equals(link.getId()))) {
            chat.addLink(linkEntity);
        }

        return link;
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, URI url) {
        ChatEntity chat = chatRepository.findById(tgChatId)
                .orElseThrow(() -> new ChatNotFoundException(tgChatId));

        Link link = clientService.getLinkInformation(url)
                .orElseThrow(() -> new ResourceNotFoundException("No source on this link found."));

        Iterator<SubscriptionEntity> iterator = chat.getChatSubscriptions().iterator();
        while (iterator.hasNext()) {
            var linkEntity = iterator.next().getLink();
            if (linkEntity.getUrl().equals(link.getUrl())) {
                link.setId(linkEntity.getId());
                iterator.remove();
                break;
            }
        }

        if (link.getId() == null) {
            throw new ResourceNotFoundException();
        }

        chatRepository.save(chat);
        return link;
    }

    @Override
    @Transactional
    public List<Link> listAll(long tgChatId) {
        return chatRepository.findById(tgChatId)
                .orElseThrow(() -> new ChatNotFoundException(tgChatId))
                .getChatSubscriptions()
                .stream().map(e -> conversionService.convert(e.getLink(), Link.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Long> getChatIdsByLinkId(long linkId) {
        System.out.println(linkRepository.findById(linkId)
                .orElseThrow(ResourceNotFoundException::new));
        return linkRepository.findById(linkId)
                .orElseThrow(ResourceNotFoundException::new)
                .getSubscriptionsOnLink()
                .stream().map(e -> e.getChat().getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Link> findAllTrackingLinksLastCheckedBefore(LocalDateTime lastCheckTime) {
        return linkRepository.findByLastCheckedAtBefore(Timestamp.valueOf(lastCheckTime))
                .stream().map(link -> conversionService.convert(link, Link.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateLinksSetLastChecked(List<Link> links, LocalDateTime lastCheckTime) {
        linkRepository.saveAll(
                links.stream().map(link -> {
                    LinkEntity entity = conversionService.convert(link, LinkEntity.class);
                    entity.setLastCheckedAt(Timestamp.valueOf(lastCheckTime));
                    return entity;
                }).toList()
        );
    }
}
