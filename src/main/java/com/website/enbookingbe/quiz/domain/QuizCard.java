package com.website.enbookingbe.quiz.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuizCard {
    private Integer quizId;
    private Integer cardId;
    private QuizCardStatus status;
}
