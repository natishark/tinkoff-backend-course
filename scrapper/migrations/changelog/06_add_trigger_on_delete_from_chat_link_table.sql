--liquibase formatted sql

--changeset natishark:6.2.1.1
CREATE OR REPLACE FUNCTION delete_link_on_delete_from_chat_link_table() RETURNS trigger AS '
    BEGIN
        if (select count(*) from chat_link cl where cl.link_id = OLD.link_id) = 0
        then delete from links l where l.id = OLD.link_id;
        end if;
        return OLD;
    END;
' LANGUAGE  plpgsql;

CREATE OR REPLACE TRIGGER trigger_link_delete
    AFTER DELETE ON chat_link FOR EACH ROW
EXECUTE PROCEDURE delete_link_on_delete_from_chat_link_table();