package com.website.enbookingbe.quiz.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class UserCardId implements Serializable {
    private static final long serialVersionUID = 8871769013771615725L;
    @NotNull
    @Column(name = "card_id", nullable = false)
    private Integer cardId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    public UserCardId(Integer cardId, Integer userId) {
        this.cardId = cardId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserCardId entity = (UserCardId) o;
        return Objects.equals(this.cardId, entity.cardId) &&
            Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, userId);
    }

}