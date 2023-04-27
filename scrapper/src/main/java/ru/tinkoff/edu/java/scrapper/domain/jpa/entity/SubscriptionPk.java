package ru.tinkoff.edu.java.scrapper.domain.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPk implements Serializable {
    private Long linkId;
    private Long chatId;
}
