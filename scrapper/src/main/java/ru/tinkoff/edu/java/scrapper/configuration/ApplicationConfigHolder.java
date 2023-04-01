package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfigHolder {

    @Getter
    private static ApplicationConfig config;

    @Autowired
    public ApplicationConfigHolder(ApplicationConfig config) {
        ApplicationConfigHolder.config = config;
    }
}
