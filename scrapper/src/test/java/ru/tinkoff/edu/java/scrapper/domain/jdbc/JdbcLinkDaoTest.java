package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties="app.database-access-type=jdbc")
public class JdbcLinkDaoTest extends IntegrationEnvironment {

    @Autowired
    private JdbcLinkDao linkDao;

    @Transactional
    @Rollback
    @Test
    public void add_linkWithAllFields() {
        Link link = new Link()
                .setAnswerCount(46)
                .setPushedAt(Timestamp.valueOf(LocalDateTime.of(2000, 4, 5, 12, 0)))
                .setUrl("url")
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.of(2000, 4, 5, 12, 0)));

        link.setId(linkDao.add(link).getId());

        assertNotNull(link.getId());
        Optional<Link> returned = linkDao.findByUrl("url");
        assertTrue(returned.isPresent());
        assertEquals(link, returned.get());
    }
}
