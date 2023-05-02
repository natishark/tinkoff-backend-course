package ru.tinkoff.edu.java.scrapper.service.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkEntity;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;

public class LinkToLinkEntityConverter implements Converter<Link, LinkEntity> {

    @Override
    public LinkEntity convert(@NotNull Link link) {
        var entity = new LinkEntity();
        entity.setId(link.getId());
        entity.setUrl(link.getUrl());
        entity.setUpdatedAt(link.getUpdatedAt());
        entity.setPushedAt(link.getPushedAt());
        entity.setAnswerCount(link.getAnswerCount());

        return entity;
    }
}
