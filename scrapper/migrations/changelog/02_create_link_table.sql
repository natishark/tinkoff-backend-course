--liquibase formatted sql

--changeset natishark:init_db
CREATE SEQUENCE IF NOT EXISTS link_seq;

CREATE TABLE IF NOT EXISTS links
(
    id         bigint PRIMARY KEY DEFAULT nextVal('link_seq'),
    url        text        NOT NULL UNIQUE,
    updated_at timestamptz NOT NULL
);