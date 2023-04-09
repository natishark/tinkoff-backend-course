package com.natishark.course.tinkoff.bot.dto;

import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

@Validated
public record RemoveLinkRequest(
        @NotNull("The 'link' field is required in the request body")
        URI link
) {
}
