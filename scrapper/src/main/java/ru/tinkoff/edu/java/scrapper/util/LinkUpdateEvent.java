package ru.tinkoff.edu.java.scrapper.util;

import lombok.Getter;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

public class LinkUpdateEvent {

    private static final String DESCRIPTION_HEADER = "Updates by link %s:";

    private boolean updated = false;
    private final StringBuilder description;
    private final Link oldLink;
    @Getter
    private final Link link;

    public LinkUpdateEvent(Link oldLink, Link link) {
        this.oldLink = oldLink;
        this.link = link;
        description = new StringBuilder(DESCRIPTION_HEADER.formatted(link.getUrl()));

        checkUpdatedAt();
        checkPushedAt();
        checkAnswerCount();
    }

    public boolean wasUpdated() {
        return updated;
    }

    public String getDescription() {
        return description.toString();
    }

    private void checkUpdatedAt() {
        if (link.getUpdatedAt().after(oldLink.getUpdatedAt())) {
            updated = true;
            description.append("\nSome updates fixed");
        }
    }

    private void checkPushedAt() {
        if (link.getPushedAt() != null && (oldLink.getPushedAt() == null
                || link.getPushedAt().after(oldLink.getPushedAt()))) {
            updated = true;
            description.append("\nNew push spotted");
        }
    }

    private void checkAnswerCount() {
        int newAnswersCount = 0;
        if (link.getAnswerCount() != null && oldLink.getAnswerCount() == null) {
            newAnswersCount = link.getAnswerCount();
        }
        if (link.getAnswerCount() != null && oldLink.getAnswerCount() != null) {
            newAnswersCount = link.getAnswerCount() - oldLink.getAnswerCount();
        }

        if (newAnswersCount > 0) {
            updated = true;
            description.append("\nAdded %d answers".formatted(newAnswersCount));
        }
    }
}
