package ru.tinkoff.edu.java.scrapper.service.jpa;

import com.natishark.course.tinkoff.bot.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;
import ru.tinkoff.edu.java.scrapper.service.ClientService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JpaLinkServiceTest extends IntegrationEnvironment {

    @MockBean
    private ClientService clientService;
    @Autowired
    private JpaLinkService linkService;
    @Autowired
    private JpaChatService chatService;

    @Transactional
    @Rollback
    @Test
    public void add_validLinkAndExistingChat_successfullyAdded() {
        long chatId = 1111;
        URI url = URI.create("https://github.com/link/link");
        Link link = new Link()
                .setUrl(String.valueOf(url))
                .setAnswerCount(2)
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chatService.register(chatId);
        Mockito.when(clientService.getLinkInformation(url)).thenReturn(Optional.of(link));

        Link resultLink = linkService.add(chatId, url);

        assertEquals(link.getUrl(), resultLink.getUrl());
        assertEquals(link.getAnswerCount(), resultLink.getAnswerCount());
        assertEquals(link.getUpdatedAt(), resultLink.getUpdatedAt());
        var links = linkService.listAll(chatId);
        assertEquals(1, links.size());
        assertEquals(link.getUrl(), links.get(0).getUrl());
        assertNotNull(links.get(0).getId());
    }

    @Transactional
    @Rollback
    @Test
    public void add_addLinkToNotExistingChat_ResourceNotFound() {
        URI url = URI.create("https://github.com/link/link");
        Link link = new Link()
                .setUrl(String.valueOf(url))
                .setAnswerCount(2)
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        Mockito.when(clientService.getLinkInformation(url)).thenReturn(Optional.of(link));

        assertThrows(ResourceNotFoundException.class, () -> linkService.add(12345L, url));
    }

    @Transactional
    @Rollback
    @Test
    public void remove_removeExistingLink_emptyTable() {
        long chatId = 1111;
        URI url = URI.create("https://github.com/link/linkkk");
        Link link = new Link()
                .setUrl(String.valueOf(url))
                .setPushedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chatService.register(chatId);
        Mockito.when(clientService.getLinkInformation(url)).thenReturn(Optional.of(link));
        linkService.add(chatId, url);

        linkService.remove(chatId, url);

        assertEquals(0, linkService.listAll(chatId).size());
    }

    @Transactional
    @Rollback
    @Test
    public void remove_removeNotExistingLink_ResourceNotFound() {
        long chatId = 1111;
        URI url = URI.create("https://github.com/link/linkkk");
        Link link = new Link()
                .setUrl(String.valueOf(url))
                .setPushedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chatService.register(chatId);
        Mockito.when(clientService.getLinkInformation(url)).thenReturn(Optional.of(link));

        assertThrows(ResourceNotFoundException.class, () -> linkService.remove(chatId, url));
    }

    @Transactional
    @Rollback
    @Test
    public void listAll_addFewLinks_returnsAddedLinks() {
        long chatId = 1111;
        URI url1 = URI.create("https://github.com/link/link");
        Link link1 = new Link()
                .setUrl(String.valueOf(url1))
                .setAnswerCount(2)
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        URI url2 = URI.create("https://github.com/link2/link2");
        Link link2 = new Link()
                .setUrl(String.valueOf(url2))
                .setAnswerCount(7)
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chatService.register(chatId);
        Mockito.when(clientService.getLinkInformation(url1)).thenReturn(Optional.of(link1));
        Mockito.when(clientService.getLinkInformation(url2)).thenReturn(Optional.of(link2));
        Link resultLink1 = linkService.add(chatId, url1);
        Link resultLink2 = linkService.add(chatId, url2);

        List<Link> links = linkService.listAll(chatId);

        assertEquals(2, links.size());
        assertTrue(links.contains(resultLink1));
        assertTrue(links.contains(resultLink2));
    }

    @Transactional
    @Rollback
    @Test
    public void listAll_dontAddLinks_returnsEmptyList() {
        long chatId = 1111;

        chatService.register(chatId);

        List<Link> links = linkService.listAll(chatId);

        assertEquals(0, links.size());
    }

    @Transactional
    @Rollback
    @Test
    public void getChatIdsByLinkId_fewChatsSubscribedOnLink_notEmptyIdsList() {
        long chat1Id = 1111;
        long chat2Id = 123456;
        URI url = URI.create("https://github.com/link/link");
        Link link = new Link()
                .setUrl(String.valueOf(url))
                .setAnswerCount(2)
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chatService.register(chat1Id);
        chatService.register(chat2Id);
        Mockito.when(clientService.getLinkInformation(url)).thenReturn(Optional.of(link));
        Link resultLink = linkService.add(chat1Id, url);
        linkService.add(chat2Id, url);

        List<Long> chatIds = linkService.getChatIdsByLinkId(resultLink.getId());

        assertEquals(2, chatIds.size());
        assertTrue(chatIds.contains(chat1Id));
        assertTrue(chatIds.contains(chat2Id));
    }
}
