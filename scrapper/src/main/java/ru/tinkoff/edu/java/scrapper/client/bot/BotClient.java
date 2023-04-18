package ru.tinkoff.edu.java.scrapper.client.bot;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String UPDATES_PATH = "api/updates";

    private final WebClient client;

    public BotClient() {
        this(BASE_URL);
    }

    public BotClient(String url) {
        client = WebClient.create(url);
    }

    public Mono<Void> sendUpdate(LinkUpdateRequest request) {
        return client.post().uri(UPDATES_PATH).bodyValue(request).retrieve().bodyToMono(Void.class);
    }
}
