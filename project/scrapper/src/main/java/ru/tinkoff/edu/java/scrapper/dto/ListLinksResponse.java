package ru.tinkoff.edu.java.scrapper.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ListLinksResponse {
    private final List<LinkResponse> links;
    private final int size;

    public ListLinksResponse(List<LinkResponse> links) {
        this.links = links;
        size = links.size();
    }
}
