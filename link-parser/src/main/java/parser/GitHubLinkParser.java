package parser;

import parser.result.GitHubLinkParsingResult;
import parser.result.LinkParsingResult;
import parser.util.LinkParserUtils;

import java.util.Optional;

public class GitHubLinkParser extends AbstractLinkParser {

    private static final String GITHUB_LINK_PREFIX = "https://github.com/";

    protected GitHubLinkParser(LinkHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    protected Optional<LinkParsingResult> tryParse(String link) {
        return LinkParserUtils.getLinkPathPartsSatisfyPrefix(link, GITHUB_LINK_PREFIX)
                .filter(arr -> arr.length > 1 && isValidGithubUserName(arr[0]) && isValidGitHubRepoName(arr[1]))
                .map(pathParts -> new GitHubLinkParsingResult(pathParts[0], pathParts[1]));
    }

    private boolean isValidGitHubRepoName(String s) {
        return s.matches("[0-9a-zA-Z-_]+");
    }

    private boolean isValidGithubUserName(String s) {
        return s.matches("[0-9a-zA-Z]+(-[0-9a-zA-Z]+)*");
    }
}
