package ru.tinkoff.edu.java.scrapper.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "chat_link")
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionEntity {

    @EmbeddedId
    private SubscriptionPk pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("linkId")
    private LinkEntity link;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chatId")
    private ChatEntity chat;

    public SubscriptionEntity(LinkEntity link, ChatEntity chat) {
        this.link = link;
        this.chat = chat;
        pk = new SubscriptionPk(link.getId(), chat.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionEntity that = (SubscriptionEntity) o;
        return pk.equals(that.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }
}
