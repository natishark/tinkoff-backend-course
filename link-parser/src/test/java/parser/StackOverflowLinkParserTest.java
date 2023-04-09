package parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import parser.result.LinkParsingResult;
import parser.result.StackOverflowLinkParsingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StackOverflowLinkParserTest {

    private static LinkHandler parser;

    @BeforeAll
    public static void setup() {
        parser = new StackOverflowLinkParser(null);
    }

    @Test
    public void parse_ValidStackOverflowLink_ValidStackOverflowLinkParsingResult() {
        String link = "https://stackoverflow.com/questions/75917310/php-encryption-usb-key";

        LinkParsingResult result = parser.parse(link);

        assertEquals(new StackOverflowLinkParsingResult(75917310), result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "stackoverflow.com/questions/75917310/php-encryption-usb-key",
            "https://stackover.com/questions/75917310/php-encryption-usb-key",
            "https://stackoverflow.comquestions/75917310/php-encryption-usb-key"
    })
    public void parse_InvalidStackOverflowLinkPrefix_Null(String link) {
        LinkParsingResult result = parser.parse(link);

        assertNull(result);
    }

    @Test
    public void parse_StackOverflowLinkOnlyWithPrefix_Null() {
        String link = "https://stackoverflow.com/";

        LinkParsingResult result = parser.parse(link);

        assertNull(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://stackoverflow.com/questions//php-encryption-usb-key",
            "https://stackoverflow.com/questions/7591 7310/php-encryption-usb-key",
            "https://stackoverflow.com/questions/-75917310/php-encryption-usb-key",
            "https://stackoverflow.com/questions/75_917_310/php-encryption-usb-key",
            "https://stackoverflow.com/questions/hi/php-encryption-usb-key",
            "https://stackoverflow.com/questions/75917310p/php-encryption-usb-key",
            "https://stackoverflow.com/questions/99999999999999/php-encryption-usb-key"
    })
    public void parse_StackOverflowLinkWithInvalidQuestionId_Null(String link) {
        LinkParsingResult result = parser.parse(link);

        assertNull(result);
    }
}
