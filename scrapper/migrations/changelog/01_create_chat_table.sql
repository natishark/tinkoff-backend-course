--liquibase formatted sql

--changeset natishark:init_db
CREATE TABLE IF NOT EXISTS chats
(
    id bigint PRIMARY KEY
);