package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JdbcLinkDaoTest extends IntegrationEnvironment {

    @Autowired
    private JdbcLinkDao linkDao;

    @Transactional
    @Rollback
    @Test
    public void add_linkWithAllFields() {
        Link link = new Link()
                .setAnswerCount(46)
                .setPushedAt(Timestamp.valueOf(LocalDateTime.MIN))
                .setUrl("url")
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.MAX));

        link.setId(linkDao.add(link).getId());

        assertNotNull(link.getId());
        assertEquals(link, linkDao.findByUrl("url").get());
    }
}
