package ru.tinkoff.edu.java.scrapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SimpleTest extends IntegrationEnvironment {

    @Test
    @SneakyThrows
    public void testTestContainerWork() {
        Connection connection = DriverManager
                .getConnection(DB_CONTAINER.getJdbcUrl(), DB_CONTAINER.getUsername(), DB_CONTAINER.getPassword());

        connection.createStatement().execute("INSERT INTO chats VALUES (12345)");
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM chats");

        resultSet.next();
        long result = resultSet.getLong(1);
        assertEquals(12345, result);
        assertFalse(resultSet.next());
    }
}
