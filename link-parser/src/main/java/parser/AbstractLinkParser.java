package parser;

import parser.result.LinkParsingResult;

import java.util.Optional;

public abstract class AbstractLinkParser implements LinkHandler {

    protected final LinkHandler nextHandler;

    protected AbstractLinkParser(LinkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract Optional<LinkParsingResult> tryParse(String link);

    @Override
    public LinkParsingResult parse(String link) {
        return tryParse(link).orElse(nextHandler == null ? null : nextHandler.parse(link));
    }
}
