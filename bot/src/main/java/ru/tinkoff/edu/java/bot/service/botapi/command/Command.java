package ru.tinkoff.edu.java.bot.service.botapi.command;

public enum Command {
    START("start", "Start communicating with me"),
    HELP("help", "Output a list of commands"),
    TRACK("track", "Start tracking the link"),
    UNTRACK("untrack", "Stop tracking the link"),
    LIST("list", "Show a list of tracking links");

    public final String name;
    public final String description;
    public final String representation;

    Command(String name, String description) {
        this.name = name;
        this.description = description;
        representation = "/" + name;
    }
}
