package ru.tinkoff.edu.java.scrapper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    private Long id;
    private String url;
    private Timestamp updatedAt;
    private Timestamp pushedAt;
    private Integer answerCount;

    public Link setId(Long id) {
        this.id = id;
        return this;
    }

    public Link setUrl(String url) {
        this.url = url;
        return this;
    }

    public Link setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Link setPushedAt(Timestamp pushedAt) {
        this.pushedAt = pushedAt;
        return this;
    }

    public Link setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
        return this;
    }
}
