package ru.tinkoff.edu.java.scrapper.client.stackoverflow;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.dto.QuestionRequest;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.dto.QuestionResponse;

public class StackOverflowClient {

    private static final String BASE_STACKOVERFLOW_URL = "https://api.stackexchange.com/";
    private static final String API_VERSION = "2.3";
    private static final String QUESTIONS_PATH = "questions";
    private static final String SITE_QUERY_PARAM_NAME = "site";
    private static final String SITE_QUERY_PARAM_VALUE = "stackoverflow";

    private final WebClient stackOverflowClient;

    public StackOverflowClient(String url) {
        stackOverflowClient = WebClient.create(url);
    }

    public StackOverflowClient() {
        this(BASE_STACKOVERFLOW_URL);
    }

    public Mono<QuestionResponse> getQuestionInformation(QuestionRequest request) {
        return stackOverflowClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment(API_VERSION)
                        .pathSegment(QUESTIONS_PATH)
                        .path(request.id())
                        .queryParam(SITE_QUERY_PARAM_NAME, SITE_QUERY_PARAM_VALUE)
                        .build())
                .exchangeToMono(response -> response.statusCode().is2xxSuccessful()
                        ? response.bodyToMono(QuestionResponse.class)
                        : Mono.empty()
                );
    }
}
