--liquibase formatted sql

--changeset natishark:6.2.1.4
ALTER TABLE links ADD COLUMN last_checked_at timestamptz DEFAULT NOW();