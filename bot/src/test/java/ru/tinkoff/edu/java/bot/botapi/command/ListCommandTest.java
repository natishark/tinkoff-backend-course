package ru.tinkoff.edu.java.bot.botapi.command;

import com.natishark.course.tinkoff.bot.dto.LinkResponse;
import com.natishark.course.tinkoff.bot.dto.ListLinksResponse;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.botapi.BotApiTest;
import ru.tinkoff.edu.java.bot.botapi.util.TestUtils;
import ru.tinkoff.edu.java.bot.bot.command.ListCommand;
import ru.tinkoff.edu.java.bot.client.scrapper.ScrapperClient;

import java.net.URI;
import java.util.List;
import java.util.random.RandomGenerator;

public class ListCommandTest extends BotApiTest {

    @MockBean
    private ScrapperClient scrapperClient;

    @Autowired
    private ListCommand listCommand;

    @Test
    public void handle_EmptyLinkList_SendMessageContainsNoLinksMessage() {
        long chatId = RandomGenerator.getDefault().nextLong();
        Update update = TestUtils.initUpdate(chatId);
        Mockito.when(scrapperClient.getTrackedLinks(chatId))
                .thenReturn(Mono.just(new ListLinksResponse(List.of(), 0)));

        SendMessage result = listCommand.handle(update);

        assertValidSendMessage(
                result,
                chatId,
                "You don't have any tracking links."
        );
    }

    @Test
    public void handle_NotEmptyLinkList_SendMessageContainsFormattedMessageWithAllLinks() {
        long chatId = RandomGenerator.getDefault().nextLong();
        Update update = TestUtils.initUpdate(chatId);
        Mockito.when(scrapperClient.getTrackedLinks(chatId))
                .thenReturn(Mono.just(new ListLinksResponse(List.of(
                        new LinkResponse(0L, URI.create("https://link1.link")),
                        new LinkResponse(1L, URI.create("https://some-link2.link"))
                ), 2)));

        SendMessage result = listCommand.handle(update);

        assertValidSendMessage(
                result,
                chatId,
                """
                        <b>Your tracked links:</b>
                        • https://link1.link
                        • https://some-link2.link
                        """
        );
    }
}
