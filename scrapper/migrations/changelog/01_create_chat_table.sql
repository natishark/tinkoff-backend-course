--liquibase formatted sql

--changeset natishark:1
CREATE TABLE chats
(
    id bigint PRIMARY KEY
);