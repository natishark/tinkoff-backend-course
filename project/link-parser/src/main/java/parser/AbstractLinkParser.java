package parser;

import parser.result.LinkParsingResult;

import java.util.Optional;

public abstract class AbstractLinkParser {
    protected final AbstractLinkParser nextHandler;

    protected AbstractLinkParser(AbstractLinkParser nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract Optional<LinkParsingResult> tryParse(String link);

    protected LinkParsingResult parse(String link) {
        return tryParse(link).orElse(nextHandler == null ? null : nextHandler.parse(link));
    }
}
