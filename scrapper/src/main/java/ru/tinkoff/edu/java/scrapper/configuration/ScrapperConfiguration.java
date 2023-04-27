package ru.tinkoff.edu.java.scrapper.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import parser.LinkParser;
import ru.tinkoff.edu.java.scrapper.controller.converter.LinkToLinkResponseConverter;
import ru.tinkoff.edu.java.scrapper.service.converter.*;

@Configuration
public class ScrapperConfiguration implements WebMvcConfigurer {

    @Bean
    public LinkParser linkParser() {
        return new LinkParser();
    }

    @Override
    public void addFormatters(@NotNull FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(new LinkToLinkResponseConverter());
        registry.addConverter(new QuestionResponseToLinkConverter());
        registry.addConverter(new RepositoryResponseToLinkConverter());
        registry.addConverter(new ChatEntityToChatConverter());
        registry.addConverter(new LinkEntityToLinkConverter());
        registry.addConverter(new LinkToLinkEntityConverter());
    }
}
