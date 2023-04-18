package com.natishark.course.tinkoff.bot.dto;

import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.util.List;

@Validated
public record LinkUpdateRequest(
        @NotNull("The id field is required in the request body")
        Long id,
        @NotNull("The url field is required in the request body")
        URI url,
        String description,
        @NotNull("The tgChatIds field is required in the request body")
        List<Long> tgChatIds
) {
}
