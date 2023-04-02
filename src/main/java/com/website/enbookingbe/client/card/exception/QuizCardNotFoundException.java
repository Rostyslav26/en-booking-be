package com.website.enbookingbe.client.card.exception;

import com.website.enbookingbe.core.exception.NotFoundException;

public class QuizCardNotFoundException extends NotFoundException {

    public QuizCardNotFoundException(Integer quizId, Integer cardId) {
        super("Quiz card not found with quiz id: %d and card id: %d", quizId, cardId);
    }
}
