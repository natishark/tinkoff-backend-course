package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.dto.domain.Chat;
import ru.tinkoff.edu.java.scrapper.dto.domain.ChatLinkDto;
import ru.tinkoff.edu.java.scrapper.dto.domain.Link;

import javax.sql.DataSource;
import java.util.List;

public class JdbcChatLinkDao {

    private static final String INSERT_RECORD_QUERY = """
            INSERT INTO chat_link (chat_id, link_id)
            VALUES (?, ?)
            ON CONFLICT DO NOTHING""";

    private static final String DELETE_RECORD_QUERY = """
            DELETE FROM chat_link WHERE chat_id = ? AND link_id = ?""";

    private static final String SELECT_LINKS_BY_CHAT_ID_QUERY = """
            SELECT l.id AS id, url, updated_at, pushed_at, answer_count
            FROM chat_link cl JOIN links l on l.id = cl.link_id
            WHERE chat_id = ?""";

    private static final String SELECT_CHATS_BY_LINK_ID_QUERY = """
            SELECT id FROM chat_link cl join chats c on c.id = cl.chat_id
            WHERE cl.link_id = ?""";

    private final JdbcTemplate jdbcTemplate;
    private final JdbcLinkDao linkDao;

    public JdbcChatLinkDao(DataSource dataSource, JdbcLinkDao linkDao) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.linkDao = linkDao;
    }

    public Link subscribe(long chatId, Link link) {
        linkDao.add(link);

        jdbcTemplate.update(INSERT_RECORD_QUERY, chatId, link.getId());

        return link;
    }

    public void unsubscribe(ChatLinkDto id) {
        jdbcTemplate.update(DELETE_RECORD_QUERY, id.chatId(), id.linkId());
    }

    public List<Link> findAllLinksByChatId(long chatId) {
        return jdbcTemplate.query(
                SELECT_LINKS_BY_CHAT_ID_QUERY,
                JdbcLinkDao.LINK_ROW_MAPPER,
                chatId
        );
    }

    public List<Chat> findAllChatsByLinkId(long linkId) {
        return jdbcTemplate.query(
                SELECT_CHATS_BY_LINK_ID_QUERY,
                JdbcChatDao.CHAT_ROW_MAPPER,
                linkId
        );
    }
}
