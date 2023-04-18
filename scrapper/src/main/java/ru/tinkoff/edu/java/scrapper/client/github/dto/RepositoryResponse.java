package ru.tinkoff.edu.java.scrapper.client.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.time.OffsetDateTime;

public record RepositoryResponse(
        @JsonProperty("html_url")
        URI htmlUrl,
        @JsonProperty("updated_at")
        OffsetDateTime updatedAt,
        @JsonProperty("pushed_at")
        OffsetDateTime pushedAt
) {
}
