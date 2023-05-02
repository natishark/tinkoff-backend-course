package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatDao;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatLinkDao;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.ClientService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    @Bean
    public LinkService linkService(
            JdbcChatLinkDao chatLinkDao,
            ChatService chatService,
            JdbcLinkDao linkDao,
            ClientService clientService
    ) {
        return new JdbcLinkService(chatLinkDao, chatService, linkDao, clientService);
    }

    @Bean
    public ChatService chatService(JdbcChatDao chatDao) {
        return new JdbcChatService(chatDao);
    }

    @Bean
    public JdbcChatDao jdbcChatDao(DataSource dataSource) {
        return new JdbcChatDao(dataSource);
    }

    @Bean
    public JdbcLinkDao jdbcLinkDao(DataSource dataSource) {
        return new JdbcLinkDao(dataSource);
    }

    @Bean
    public JdbcChatLinkDao jdbcChatLinkDao(DataSource dataSource, JdbcLinkDao linkDao) {
        return new JdbcChatLinkDao(dataSource, linkDao);
    }
}
