package ru.tinkoff.edu.java.scrapper.dto.client.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public record QuestionResponse(List<Item> items) {

    public record Item(
            URI link,
            @JsonProperty("last_activity_date")
            OffsetDateTime lastActivityDate,
            @JsonProperty("last_edit_date")
            OffsetDateTime lastEditDate,
            @JsonProperty("answer_count")
            int answerCount
    ) {
    }
}
