package ru.tinkoff.edu.java.scrapper.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chats")
@Data
@NoArgsConstructor
public class ChatEntity {
    @Id
    private Long id;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private Set<SubscriptionEntity> chatSubscriptions = new HashSet<>();

    public ChatEntity(Long id) {
        this.id = id;
    }

    public void addLink(LinkEntity link) {
        var entity = new SubscriptionEntity(link, this);
        chatSubscriptions.add(entity);
        link.getSubscriptionsOnLink().add(entity);
    }
}
