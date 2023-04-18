package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.util.List;

public interface LinkUpdater {
    int update();
    void executeUpdate(List<Link> links);
}
