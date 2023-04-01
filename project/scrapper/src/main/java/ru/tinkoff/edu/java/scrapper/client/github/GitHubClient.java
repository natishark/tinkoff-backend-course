package ru.tinkoff.edu.java.scrapper.client.github;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.client.github.dto.RepositoryRequest;
import ru.tinkoff.edu.java.scrapper.client.github.dto.RepositoryResponse;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfigHolder;

public class GitHubClient {

    private static final String BASE_GITHUB_URL = "https://api.github.com";
    private static final String API_VERSION_HEADER_NAME = "X-GitHub-Api-Version";
    private static final String API_VERSION_HEADER_VALUE = "2022-11-28";
    private static final String ACCESS_TOKEN_PREFIX = "Bearer ";
    private static final String REPOSITORIES_PATH = "repos";

    private final WebClient githubClient;

    public GitHubClient() {
        this(BASE_GITHUB_URL);
    }

    public GitHubClient(String url) {
        final String secretToken = ApplicationConfigHolder
                .getConfig()
                .githubCredentials()
                .githubToken();

        githubClient = WebClient
                .builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        ACCESS_TOKEN_PREFIX + secretToken
                )
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
                .retrieve()
                .bodyToMono(RepositoryResponse.class);
    }
}
