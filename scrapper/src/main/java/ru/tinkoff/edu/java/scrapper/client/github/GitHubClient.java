package ru.tinkoff.edu.java.scrapper.client.github;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.client.github.RepositoryRequest;
import ru.tinkoff.edu.java.scrapper.dto.client.github.RepositoryResponse;

public class GitHubClient {

    private static final String BASE_GITHUB_URL = "https://api.github.com";
    private static final String API_VERSION_HEADER_NAME = "X-GitHub-Api-Version";
    private static final String API_VERSION_HEADER_VALUE = "2022-11-28";
    private static final String REPOSITORIES_PATH = "repos";

    private final WebClient githubClient;

    public GitHubClient() {
        this(BASE_GITHUB_URL);
    }

    public GitHubClient(String url) {
        githubClient = WebClient
                .builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(API_VERSION_HEADER_NAME, API_VERSION_HEADER_VALUE)
                .build();
    }

    public Mono<RepositoryResponse> getRepositoryInformation(RepositoryRequest request) {
        return githubClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment(REPOSITORIES_PATH)
                        .pathSegment(request.userName())
                        .path(request.repositoryName())
                        .build())
                .exchangeToMono(response -> response.statusCode().is2xxSuccessful()
                        ? response.bodyToMono(RepositoryResponse.class)
                        : Mono.empty()
                );
    }
}
