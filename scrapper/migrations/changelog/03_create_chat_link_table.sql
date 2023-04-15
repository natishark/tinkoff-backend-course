--liquibase formatted sql

--changeset natishark:init_db
CREATE TABLE IF NOT EXISTS chat_link
(
    chat_id bigint REFERENCES chats (id) NOT NULL,
    link_id bigint REFERENCES links (id) NOT NULL,
    PRIMARY KEY (chat_id, link_id)
);