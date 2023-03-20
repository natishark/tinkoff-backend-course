package parser.util;

import java.util.Optional;

public class LinkParserUtils {
    public static Optional<String[]> getLinkPathPartsSatisfyPrefix(String link, String prefix) {
        if (!link.startsWith(prefix)) {
            return Optional.empty();
        }

        return Optional.of(link.substring(prefix.length()).split("/"));
    }
}
