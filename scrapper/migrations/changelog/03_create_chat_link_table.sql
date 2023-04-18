--liquibase formatted sql

--changeset natishark:init_db
CREATE TABLE IF NOT EXISTS chat_link
(
    chat_id bigint REFERENCES chats (id) ON DELETE CASCADE NOT NULL,
    link_id bigint REFERENCES links (id) ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (chat_id, link_id)
);