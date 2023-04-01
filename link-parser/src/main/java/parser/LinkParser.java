package parser;

import parser.result.LinkParsingResult;

import java.util.ArrayList;
import java.util.List;

public class LinkParser {

    private final LinkHandler entryHandler;

    public LinkParser() {
        List<LinkHandler> parserChain = new ArrayList<>();

        parserChain.add(new GitHubLinkParser(null));
        parserChain.add(new StackOverflowLinkParser(parserChain.get(parserChain.size() - 1)));

        entryHandler = parserChain.get(parserChain.size() - 1);
    }

    public LinkParsingResult parse(final String link) {
        return entryHandler.parse(link);
    }
}
