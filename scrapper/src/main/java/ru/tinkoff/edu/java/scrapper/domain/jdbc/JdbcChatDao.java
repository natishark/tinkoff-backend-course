package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.domain.Chat;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcChatDao {

    public static final RowMapper<Chat> CHAT_ROW_MAPPER = (resultSet, rowNum) ->
            new Chat(resultSet.getLong("id"));

    private static final String INSERT_QUERY = "INSERT INTO chats VALUES (?) ON CONFLICT DO NOTHING";
    private static final String DELETE_QUERY = "DELETE FROM chats WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM chats";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM chats WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcChatDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean add(Chat chat) {
        return jdbcTemplate.update(INSERT_QUERY, chat.id()) == 1;
    }

    public void remove(Long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    public List<Chat> findAll() {
        return jdbcTemplate.query(
                SELECT_QUERY,
                CHAT_ROW_MAPPER
        );
    }

    public Optional<Chat> findById(long id) {
        return jdbcTemplate.query(
                SELECT_BY_ID_QUERY,
                CHAT_ROW_MAPPER,
                id
        ).stream().findAny();
    }
}
