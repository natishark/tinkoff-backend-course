package parser.result;

public record GitHubLinkParsingResult(String userName, String repositoryName)
        implements LinkParsingResult {
}
