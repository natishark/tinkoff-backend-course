package parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.result.GitHubLinkParsingResult;
import parser.result.LinkParsingResult;
import parser.result.StackOverflowLinkParsingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LinkParserTest {

    private static LinkParser parser;

    @BeforeAll
    public static void setup() {
        parser = new LinkParser();
    }

    @Test
    public void parse_ValidGitHubLink_ValidGithubParsingResult() {
        String link = "https://github.com/sanyarnd/applocker";

        LinkParsingResult result = parser.parse(link);

        assertEquals(new GitHubLinkParsingResult("sanyarnd", "applocker"), result);
    }

    @Test
    public void parse_ValidStackOverflowLink_ValidGithubParsingResult() {
        String link = "https://stackoverflow.com/questions/60323359/how-to-send-a-body-with-http-delete-when-using-webflux";

        LinkParsingResult result = parser.parse(link);

        assertEquals(new StackOverflowLinkParsingResult(60323359), result);
    }

    @Test
    public void parse_InvalidStackOverflowOrGitHubLink_Null() {
        String link = "https://vk.com/friends";

        LinkParsingResult result = parser.parse(link);

        assertNull(result);
    }
}
