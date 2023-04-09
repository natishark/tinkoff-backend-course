package ru.tinkoff.edu.java.bot.client.scrapper;

import com.natishark.course.tinkoff.bot.dto.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ScrapperClient {

    private static final String BASE_GITHUB_URL = "http://localhost:8088";
    private static final String TG_CHAT_PATH = "api/tg-chat";
    private static final String LINKS_PATH = "api/links";
    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";

    private final WebClient client;

    public ScrapperClient() {
        this(BASE_GITHUB_URL);
    }

    public ScrapperClient(String url) {
        client = WebClient.create(url);
    }

    public Mono<Boolean> registerChat(Long chatId) {
        return performChatRequest(chatId, client.post());
    }

    public Mono<Boolean> deleteChat(Long chatId) {
        return performChatRequest(chatId, client.delete());
    }

    public Mono<ListLinksResponse> getTrackedLinks(Long chatId) {
        return client
                .get()
                .uri(LINKS_PATH)
                .header(TG_CHAT_ID_HEADER, chatId.toString())
                .retrieve()
                .bodyToMono(ListLinksResponse.class);
    }

    public Mono<LinkResponse> addLinkTracking(Long chatId, URI link) {
        return performLinkTrackingRequest(HttpMethod.POST, chatId, new AddLinkRequest(link));
    }

    public Mono<LinkResponse> removeLinkTracking(Long chatId, URI link) {
        return performLinkTrackingRequest(HttpMethod.DELETE, chatId, new RemoveLinkRequest(link));
    }

    private Mono<LinkResponse> performLinkTrackingRequest(HttpMethod method, Long chatId, Object body) {
        return client
                .method(method)
                .uri(LINKS_PATH)
                .header(TG_CHAT_ID_HEADER, chatId.toString())
                .bodyValue(body)
                .exchangeToMono(response -> response.statusCode().is2xxSuccessful()
                                ? response.bodyToMono(LinkResponse.class)
                                : Mono.empty());
    }

    private Mono<Boolean> performChatRequest(Long chatId, WebClient.RequestHeadersUriSpec<?> spec) {
        return spec
                .uri(uriBuilder -> uriBuilder
                        .pathSegment(TG_CHAT_PATH)
                        .path(chatId.toString())
                        .build())
                .exchangeToMono(response -> Mono.just(response.statusCode().is2xxSuccessful()));
    }
}
