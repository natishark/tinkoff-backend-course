--liquibase formatted sql

--changeset natishark:1
CREATE SEQUENCE link_seq;

CREATE TABLE links
(
    id         bigint PRIMARY KEY DEFAULT nextVal('link_seq'),
    url        text                         NOT NULL,
    updated_at timestamp                    NOT NULL,
    chat_id    bigint REFERENCES chats (id) NOT NULL
);