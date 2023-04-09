package ru.tinkoff.edu.java.bot.bot.util;

import java.util.List;

public class HtmlMessageBuilder {

    private final StringBuilder sb;

    public HtmlMessageBuilder() {
        sb = new StringBuilder();
    }

    public HtmlMessageBuilder addBoldLine(String s) {
        sb.append("<b>").append(s).append("</b>").append("\n");
        return this;
    }

    private void addListItem(String s) {
        sb.append("• ").append(s).append("\n");
    }

    public HtmlMessageBuilder addList(List<String> items) {
        items.forEach(this::addListItem);
        return this;
    }

    public HtmlMessageBuilder add(String s) {
        sb.append(s);
        return this;
    }

    public String build() {
        return sb.toString();
    }
}
