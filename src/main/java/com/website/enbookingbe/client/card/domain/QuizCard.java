package com.website.enbookingbe.client.card.domain;

import com.website.enbookingbe.client.card.model.QuizCardStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizCard {
    private Integer quizId;
    private Integer cardId;
    private QuizCardStatus status;

    public QuizCard(Integer quizId, Integer cardId) {
        this.quizId = quizId;
        this.cardId = cardId;
        this.status = QuizCardStatus.IN_PROGRESS;
    }
}
