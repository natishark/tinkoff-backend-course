package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.util.List;

@Validated
public record LinkUpdateRequest(
        @NotNull(message = "The id field is required in the request body")
        Long id,
        @NotNull(message = "The url field is required in the request body")
        URI url,
        String description,
        @NotNull(message = "The tgChatIds field is required in the request body")
        List<Long> tgChatIds
) {
}
