package ru.tinkoff.edu.java.scrapper.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "links")
@Data
@NoArgsConstructor
public class LinkEntity {

    @Id
    @GeneratedValue(generator = "link_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "link_seq", sequenceName = "link_seq", allocationSize = 1)
    private Long id;
    private String url;
    private Timestamp updatedAt;
    private Timestamp pushedAt;
    private Integer answerCount;
    private Timestamp lastCheckedAt;

@OneToMany(mappedBy = "link", cascade = CascadeType.ALL)
    private List<SubscriptionEntity> subscriptionsOnLink = new ArrayList<>();

    public void addChat(ChatEntity chat) {
        var entity = new SubscriptionEntity(this, chat);
        subscriptionsOnLink.add(entity);
        chat.getChatSubscriptions().add(entity);
    }
}
