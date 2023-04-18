package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.util.LinkUpdateEvent;

import java.util.List;

public interface LinkUpdater {
    int update();
    void executeUpdate(List<LinkUpdateEvent> events);
}
