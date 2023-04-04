package parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import parser.result.GitHubLinkParsingResult;
import parser.result.LinkParsingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GitHubLinkParserTest {

    private static LinkHandler parser;

    @BeforeAll
    public static void setup() {
        parser = new GitHubLinkParser(null);
    }

    @Test
    public void parse_ValidGithubLinkNoTrailingSlash_ValidGithubLinkParsingResult() {
        parse_ValidGithubLink_ValidGithubLinkParsingResult(
                "https://github.com/Torantulino/Auto-GPT",
                "Torantulino",
                "Auto-GPT"
        );
    }

    @Test
    public void parse_ValidGithubLinkWithTrailingSlash_ValidGithubLinkParsingResult() {
        parse_ValidGithubLink_ValidGithubLinkParsingResult(
                "https://github.com/PathOfBuildingCommunity/PathOfBuilding/",
                "PathOfBuildingCommunity",
                "PathOfBuilding"
        );
    }

    @Test
    public void parse_ValidGithubLinkToSomethingInsideRepository_ValidGithubLinkParsingResult() {
        parse_ValidGithubLink_ValidGithubLinkParsingResult(
                "https://github.com/lm-sys/FastChat/pull/138/commits/bfd210126734601ccc561e4e7877086c6018ee58",
                "lm-sys",
                "FastChat"
        );
    }

    @Test
    public void parse_ValidGithubLinkWithUnusualRepoName_ValidGithubLinkParsingResult() {
        parse_ValidGithubLink_ValidGithubLinkParsingResult(
                "https://github.com/a-b-c/-_",
                "a-b-c",
                "-_"
        );
    }

    @Test
    public void parse_InvalidGithubLinkIndistinguishableFromValid_ValidGithubLinkParsingResult() {
        parse_ValidGithubLink_ValidGithubLinkParsingResult(
                "https://github.com/features/actions",
                "features",
                "actions"
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://github.ru/Torantulino/Auto-GPT",
            "github.com/Torantulino/Auto-GPT"
    })
    public void parser_InvalidGithubLinkPrefix_Null(String link) {
        parser_InvalidGithubLink_Null(link);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://github.com/",
            "https://github.com/ppalaga",
            "https://github.com/ppalaga?tab=stars"
    })
    public void parser_InvalidGithubLinkWithTooLittlePath_Null(String link) {
        parser_InvalidGithubLink_Null(link);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://github.com/ppalaga/",
            "https://github.com//Auto-GPT",
            "https://github.com/ /Auto-GPT",
            "https://github.com/привет/Auto-GPT",
            "https://github.com/look/привет",
            "https://github.com/Toran tulino/Auto-GPT",
            "https://github.com/Toran,tulino/Auto-GPT",
            "https://github.com/Toran&tulino/Auto-GPT",
            "https://github.com/-/Auto-GPT",
            "https://github.com/-t/Auto-GPT",
            "https://github.com/t-/Auto-GPT",
            "https://github.com/t_t/Auto-GPT",
            "https://github.com///"
    })
    public void parser_InvalidUserOrRepositoryName_Null(String link) {
        parser_InvalidGithubLink_Null(link);
    }

    private void parser_InvalidGithubLink_Null(String link) {
        LinkParsingResult result = parser.parse(link);

        assertNull(result);
    }

    private void parse_ValidGithubLink_ValidGithubLinkParsingResult(
            String link,
            String expectedUser,
            String expectedRepository
    ) {
        LinkParsingResult result = parser.parse(link);

        assertEquals(new GitHubLinkParsingResult(expectedUser, expectedRepository), result);
    }
}
