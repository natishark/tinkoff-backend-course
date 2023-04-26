package ru.tinkoff.edu.java.scrapper.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LinkUpdaterScheduler {

    private static Logger LOGGER;
    private static int counter = 0;

    private final LinkUpdater linkUpdater;

    public LinkUpdaterScheduler(LinkUpdater linkUpdater) {
        this.linkUpdater = linkUpdater;
        LOGGER = Logger.getLogger(LinkUpdaterScheduler.class.getName());
    }

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        int updated = linkUpdater.update();

        LOGGER.log(Level.INFO, "Update called: %d. Links updated: %d".formatted(++counter, updated));
    }
}
