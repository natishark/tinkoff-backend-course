package ru.tinkoff.edu.java.bot.service;

import com.natishark.course.tinkoff.bot.dto.LinkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.client.scrapper.ScrapperClient;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScrapperService {

    private final ScrapperClient scrapperClient;

    @Autowired
    public ScrapperService(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    public void registerChat(Long chatId) {
        scrapperClient.registerChat(chatId).block();
    }

    public List<String> getTrackedLinks(Long chatId) {
        return Objects.requireNonNull(scrapperClient.getTrackedLinks(chatId).block())
                .links()
                .stream().map(linkResponse -> linkResponse.url().toString())
                .collect(Collectors.toList());
    }

    public Optional<LinkResponse> addLinkTracking(Long chatId, String link) {
        return scrapperClient.addLinkTracking(
                chatId,
                URI.create(link)
        ).blockOptional();
    }

    public Optional<LinkResponse> removeLinkTracking(Long chatId, String link) {
        return scrapperClient.removeLinkTracking(
                chatId,
                URI.create(link)
        ).blockOptional();
    }
}
