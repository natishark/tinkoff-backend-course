package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.ClientService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public LinkService linkService(
            JpaLinkRepository linkRepository,
            JpaChatRepository chatRepository,
            ClientService clientService,
            ConversionService conversionService
    ) {
        return new JpaLinkService(linkRepository, chatRepository, clientService, conversionService);
    }

    @Bean
    public ChatService chatService(
            JpaChatRepository chatRepository,
            ConversionService conversionService
    ) {
        return new JpaChatService(chatRepository, conversionService);
    }
}
