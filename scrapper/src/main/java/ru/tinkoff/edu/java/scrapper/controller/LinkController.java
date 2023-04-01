package ru.tinkoff.edu.java.scrapper.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

import java.util.List;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    @GetMapping
    public ListLinksResponse getTrackedLinks(@RequestHeader(name = "Tg-Chat-Id") long tgChatId) {
        return new ListLinksResponse(List.of());
    }

    @PostMapping
    public LinkResponse addLinkTracking(
            @RequestHeader(name = "Tg-Chat-Id") long tgChatId,
            @RequestBody @Valid AddLinkRequest addLinkRequest
    ) {
        return new LinkResponse(0L, addLinkRequest.link());
    }

    @DeleteMapping
    public LinkResponse removeLinkTracking(
            @RequestHeader(name = "Tg-Chat-Id") long tgChatId,
            @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) {
        return new LinkResponse(0L, removeLinkRequest.link());
    }
}
