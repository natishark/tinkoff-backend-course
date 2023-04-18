package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcLinkDao {

    public static final RowMapper<Link> LINK_ROW_MAPPER = (resultSet, rowNum) -> new Link(
            resultSet.getLong("id"),
            resultSet.getString("url"),
            resultSet.getTimestamp("updated_at")
    );

    private static final String INSERT_QUERY = """
            INSERT INTO links (url, updated_at) VALUES (?, ?)
            ON CONFLICT (url) DO UPDATE SET id = links.id RETURNING id""";

    private static final String DELETE_QUERY = "DELETE FROM links WHERE id = ?";

    private static final String SELECT_QUERY = "SELECT * FROM links";

    private static final String SELECT_BY_URL_QUERY = "SELECT * FROM links WHERE url = ?";

    private static final String UPDATE_QUERY =
            "UPDATE links SET updated_at = ?, last_checked_at = ? WHERE id = ?";

    private static final String SELECT_BY_LAST_CHECKED_AT_QUERY = """
            SELECT id, url, updated_at
            FROM links l join chat_link cl on l.id = cl.link_id
            WHERE last_checked_at < ? GROUP BY id""";

    private final JdbcTemplate jdbcTemplate;

    public JdbcLinkDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Link add(Link link) {
        link.setId(jdbcTemplate.queryForObject(
                INSERT_QUERY,
                Long.class,
                link.getUrl(),
                link.getUpdatedAt()
        ));

        return link;
    }

    public boolean remove(long id) {
        return jdbcTemplate.update(DELETE_QUERY, id) == 1;
    }

    public List<Link> findAll() {
        return jdbcTemplate.query(SELECT_QUERY, LINK_ROW_MAPPER);
    }

    public List<Link> findAllTrackingLinksLastCheckedBefore(Timestamp lastCheckTimeStamp) {
        return jdbcTemplate.query(
                SELECT_BY_LAST_CHECKED_AT_QUERY,
                LINK_ROW_MAPPER,
                lastCheckTimeStamp
        );
    }

    public Optional<Link> findByUrl(String url) {
        return jdbcTemplate.query(
                SELECT_BY_URL_QUERY,
                LINK_ROW_MAPPER,
                url
        ).stream().findAny();
    }

    public void batchUpdate(List<Link> links, Timestamp lastCheckTimeStamp) {
        jdbcTemplate.batchUpdate(UPDATE_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NotNull PreparedStatement ps, int i) throws SQLException {
                var link = links.get(i);
                ps.setTimestamp(1, link.getUpdatedAt());
                ps.setTimestamp(2, lastCheckTimeStamp);
                ps.setLong(3, link.getId());
            }

            @Override
            public int getBatchSize() {
                return links.size();
            }
        });
    }
}