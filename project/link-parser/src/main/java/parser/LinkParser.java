package parser;

import parser.result.LinkParsingResult;

public class LinkParser {
    private final String link;

    public LinkParser(String link) {
        this.link = link;
    }

    public LinkParsingResult parse() {
        final var gitHubLinkParser = new GitHubLinkParser(null);
        final var stackOverflowLinkParser = new StackOverflowLinkParser(gitHubLinkParser);

        return stackOverflowLinkParser.parse(link);
    }
}
