package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public GitHubClient gitHubClient() {
        System.out.println("Created GitHubClient bean.");
        return new GitHubClient();
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        System.out.println("Created StackOverflowClient bean.");
        return new StackOverflowClient();
    }
}
