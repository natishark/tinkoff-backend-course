package ru.tinkoff.edu.java.scrapper.service;

import org.springframework.core.convert.ConversionService;
import parser.LinkParser;
import parser.result.GitHubLinkParsingResult;
import parser.result.LinkParsingResult;
import parser.result.StackOverflowLinkParsingResult;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.github.dto.RepositoryRequest;
import ru.tinkoff.edu.java.scrapper.client.github.dto.RepositoryResponse;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.dto.QuestionRequest;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.dto.QuestionResponse;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.net.URI;
import java.util.Optional;

public abstract class AbstractLinkService implements LinkService {

    private final LinkParser linkParser;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final ConversionService conversionService;

    protected AbstractLinkService(
            LinkParser linkParser,
            GitHubClient gitHubClient,
            StackOverflowClient stackOverflowClient,
            ConversionService conversionService) {
        this.linkParser = linkParser;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.conversionService = conversionService;
    }

    @Override
    public Optional<Link> callApiByUrlAndConvertToLink(URI url) {
        LinkParsingResult result = linkParser.parse(String.valueOf(url));

        if (result == null) {
            return Optional.empty();
        }

        return switch (result) {
            case GitHubLinkParsingResult ghpResult -> githubLinkParsingResultToLink(ghpResult);
            case StackOverflowLinkParsingResult soResult -> stackOverflowLinkParsingResultToLink(soResult);
        };
    }

    private Optional<Link> githubLinkParsingResultToLink(GitHubLinkParsingResult result) {
        Optional<RepositoryResponse> responseOptional = gitHubClient
                .getRepositoryInformation(new RepositoryRequest(result.userName(), result.repositoryName()))
                .blockOptional();

        return responseOptional.map(response -> conversionService.convert(response, Link.class));
    }

    private Optional<Link> stackOverflowLinkParsingResultToLink(StackOverflowLinkParsingResult result) {
        Optional<QuestionResponse> responseOptional = stackOverflowClient
                .getQuestionInformation(new QuestionRequest(String.valueOf(result.questionId())))
                .blockOptional();

        return responseOptional.map(response -> conversionService.convert(response, Link.class));
    }
}
