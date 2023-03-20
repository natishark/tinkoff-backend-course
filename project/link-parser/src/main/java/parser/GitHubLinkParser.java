package parser;

import parser.result.GitHubLinkParsingResult;
import parser.result.LinkParsingResult;
import parser.util.LinkParserUtils;

import java.util.Optional;

public class GitHubLinkParser extends AbstractLinkParser {

    protected GitHubLinkParser(AbstractLinkParser nextHandler) {
        super(nextHandler);
    }

    @Override
    protected Optional<LinkParsingResult> tryParse(String link) {
        return LinkParserUtils.getLinkPathPartsSatisfyPrefix(link, "https://github.com/")
                .filter(arr -> arr.length > 1 && isValidGitHubId(arr[0]) && isValidGitHubId(arr[1]))
                .map(pathParts -> new GitHubLinkParsingResult(pathParts[0], pathParts[1]));
    }

    private boolean isValidGitHubId(String s) {
        return s.matches("[0-9a-zA-Z-_]+");
    }
}
