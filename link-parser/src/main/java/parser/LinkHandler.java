package parser;

import parser.result.LinkParsingResult;

public interface LinkHandler {
    LinkParsingResult parse(String link);
}
