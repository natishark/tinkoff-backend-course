package ru.tinkoff.edu.java.scrapper.controller;

import com.natishark.course.tinkoff.bot.dto.AddLinkRequest;
import com.natishark.course.tinkoff.bot.dto.LinkResponse;
import com.natishark.course.tinkoff.bot.dto.ListLinksResponse;
import com.natishark.course.tinkoff.bot.dto.RemoveLinkRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;
    private final ConversionService conversionService;

    @GetMapping
    public ListLinksResponse getTrackedLinks(@RequestHeader(name = "Tg-Chat-Id") long tgChatId) {
        List<Link> links = linkService.listAll(tgChatId);
        return new ListLinksResponse(
                links.stream()
                        .map(link -> conversionService.convert(link, LinkResponse.class))
                        .collect(Collectors.toList()),
                links.size()
        );
    }

    @PostMapping
    public LinkResponse addLinkTracking(
            @RequestHeader(name = "Tg-Chat-Id") long tgChatId,
            @RequestBody @Valid AddLinkRequest addLinkRequest
    ) {
        return conversionService.convert(
                linkService.add(tgChatId, addLinkRequest.link()),
                LinkResponse.class
        );
    }

    @DeleteMapping
    public LinkResponse removeLinkTracking(
            @RequestHeader(name = "Tg-Chat-Id") long tgChatId,
            @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) {
        return conversionService.convert(
                linkService.remove(tgChatId, removeLinkRequest.link()),
                LinkResponse.class
        );
    }
}
