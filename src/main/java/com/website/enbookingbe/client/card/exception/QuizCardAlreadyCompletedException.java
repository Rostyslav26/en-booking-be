package com.website.enbookingbe.client.card.exception;

import com.website.enbookingbe.core.exception.BadRequestException;

public class QuizCardAlreadyCompletedException extends BadRequestException {

    public QuizCardAlreadyCompletedException(Integer quizId, Integer cardId) {
        super("Quiz card already completed with quiz id: %d and card id: %d", quizId, cardId);
    }
}
