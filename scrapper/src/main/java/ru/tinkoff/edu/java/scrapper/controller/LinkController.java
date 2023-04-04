package ru.tinkoff.edu.java.scrapper.controller;

import com.natishark.course.tinkoff.bot.dto.AddLinkRequest;
import com.natishark.course.tinkoff.bot.dto.LinkResponse;
import com.natishark.course.tinkoff.bot.dto.ListLinksResponse;
import com.natishark.course.tinkoff.bot.dto.RemoveLinkRequest;
import com.natishark.course.tinkoff.bot.exception.ResourceAlreadyExistsException;
import com.natishark.course.tinkoff.bot.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    // Это просто пока бд нет, чтобы потыкать, не бей
    private final Map<Long, List<LinkResponse>> chatLinks = new HashMap<>();

    @GetMapping
    public ListLinksResponse getTrackedLinks(@RequestHeader(name = "Tg-Chat-Id") long tgChatId) {
        final var links = chatLinks.getOrDefault(tgChatId, new ArrayList<>());
        return new ListLinksResponse(
                links,
                links.size()
        );
    }

    @PostMapping
    public LinkResponse addLinkTracking(
            @RequestHeader(name = "Tg-Chat-Id") long tgChatId,
            @RequestBody @Valid AddLinkRequest addLinkRequest
    ) {
        final var links = chatLinks.computeIfAbsent(tgChatId, k -> new ArrayList<>());
        for (LinkResponse link : links) {
            if (link.url().equals(addLinkRequest.link())) {
                throw new ResourceAlreadyExistsException("Link exists");
            }
        }
        final var response = new LinkResponse(0L, addLinkRequest.link());
        links.add(response);
        return response;
    }

    @DeleteMapping
    public LinkResponse removeLinkTracking(
            @RequestHeader(name = "Tg-Chat-Id") long tgChatId,
            @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) {
        final var oneChatLinks = chatLinks.get(tgChatId);
        if (oneChatLinks == null) {
            throw new ResourceNotFoundException("No such link");
        }
        for (int i = 0; i < oneChatLinks.size(); i++) {
            if (removeLinkRequest.link().equals(oneChatLinks.get(i).url())) {
                return oneChatLinks.remove(i);
            }
        }

        throw new ResourceNotFoundException("No such link");
    }
}
