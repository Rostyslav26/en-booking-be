package com.website.enbookingbe.quiz.entity;

import lombok.Getter;
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
public class QuizCardId implements Serializable {
    private static final long serialVersionUID = -6882024713302967634L;
    @NotNull
    @Column(name = "quiz_id", nullable = false)
    private Integer quizId;

    @NotNull
    @Column(name = "card_id", nullable = false)
    private Integer cardId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuizCardId entity = (QuizCardId) o;
        return Objects.equals(this.quizId, entity.quizId) &&
            Objects.equals(this.cardId, entity.cardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, cardId);
    }

}