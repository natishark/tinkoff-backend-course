package ru.tinkoff.edu.java.scrapper.controller.converter;

import com.natishark.course.tinkoff.bot.dto.LinkResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;

import java.net.URI;

public class LinkToLinkResponseConverter implements Converter<Link, LinkResponse> {

    @Override
    public LinkResponse convert(@NotNull Link link) {
        return new LinkResponse(link.getId(), URI.create(link.getUrl()));
    }
}
