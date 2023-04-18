package ru.tinkoff.edu.java.scrapper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    private Long id;
    private String url;
    private Timestamp updatedAt;

    public Link(String url, Timestamp updatedAt) {
        this.url = url;
        this.updatedAt = updatedAt;
    }
}
