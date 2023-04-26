package ru.tinkoff.edu.java.scrapper.service.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import ru.tinkoff.edu.java.scrapper.dto.client.github.RepositoryResponse;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;

import java.sql.Timestamp;

public class RepositoryResponseToLinkConverter implements Converter<RepositoryResponse, Link> {

    @Override
    public Link convert(@NotNull RepositoryResponse response) {
        return new Link()
                .setUrl(response.htmlUrl().toString())
                .setUpdatedAt(Timestamp.valueOf(response.updatedAt().toLocalDateTime()))
                .setPushedAt(Timestamp.valueOf(response.pushedAt().toLocalDateTime()));
    }
}
