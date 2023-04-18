--liquibase formatted sql

--changeset natishark:6.2.2.1
ALTER TABLE links
    ADD COLUMN answer_count integer;
ALTER TABLE links
    ADD COLUMN pushed_at timestamptz;