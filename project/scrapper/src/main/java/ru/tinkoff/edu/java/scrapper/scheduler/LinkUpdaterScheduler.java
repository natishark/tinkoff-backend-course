package ru.tinkoff.edu.java.scrapper.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LinkUpdaterScheduler {

    private static Logger LOGGER;
    private static int counter = 0;

    public LinkUpdaterScheduler() {
        LOGGER = Logger.getLogger(LinkUpdaterScheduler.class.getName());
    }

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        LOGGER.log(Level.INFO, "Update called: " + (++counter));
    }
}
