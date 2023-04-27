package ru.tinkoff.edu.java.scrapper.service.converter;

import org.springframework.core.convert.converter.Converter;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkEntity;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;

public class LinkEntityToLinkConverter implements Converter<LinkEntity, Link> {

    @Override
    public Link convert(LinkEntity entity) {
        return new Link()
                .setId(entity.getId())
                .setUrl(entity.getUrl())
                .setUpdatedAt(entity.getUpdatedAt())
                .setPushedAt(entity.getPushedAt())
                .setAnswerCount(entity.getAnswerCount());
    }
}
