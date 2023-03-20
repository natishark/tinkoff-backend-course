package parser;

import parser.result.LinkParsingResult;
import parser.result.StackOverflowLinkParsingResult;
import parser.util.LinkParserUtils;

import java.util.Optional;

public class StackOverflowLinkParser extends AbstractLinkParser {

    protected StackOverflowLinkParser(AbstractLinkParser nextHandler) {
        super(nextHandler);
    }

    @Override
    protected Optional<LinkParsingResult> tryParse(String link) {
        return LinkParserUtils.getLinkPathPartsSatisfyPrefix(
                        link,
                        "https://stackoverflow.com/questions/"
                )
                .filter(arr -> arr.length > 0)
                .map(arr -> arr[0])
                .filter(this::isValidId)
                .map(id -> new StackOverflowLinkParsingResult(Long.parseLong(id)));
    }

    private boolean isValidId(String s) {
        return s.matches("[0-9]+");
    }
}
