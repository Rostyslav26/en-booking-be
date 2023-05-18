package com.website.enbookingbe.quiz.exception;

import com.website.enbookingbe.exception.BadRequestException;

public class QuizCardAlreadyCompletedException extends BadRequestException {

    public QuizCardAlreadyCompletedException(Integer quizId, Integer cardId) {
        super("Quiz card already completed with quiz id: %d and card id: %d", quizId, cardId);
    }
}
