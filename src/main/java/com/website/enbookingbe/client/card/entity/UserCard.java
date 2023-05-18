package com.website.enbookingbe.client.card.entity;

import com.website.enbookingbe.core.user.management.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_card")
public class UserCard {
    @EmbeddedId
    private UserCardId id;

    @MapsId("cardId")
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "learned")
    private Boolean learned;

    @Column(name = "favorite")
    private Boolean favorite;

    public UserCard(Card card, User user) {
        this.card = card;
        this.user = user;
    }
}