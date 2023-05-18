package com.website.enbookingbe.client.card.entity;

import com.website.enbookingbe.client.card.model.QuizCardStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "quiz_card")
public class QuizCard {
    @EmbeddedId
    private QuizCardId id;

    @MapsId("quizId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @MapsId("cardId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Size(max = 10)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private QuizCardStatus status;

    public QuizCard() {
    }

    public QuizCard(Quiz quiz, Card card) {
        this.quiz = quiz;
        this.card = card;
    }
}