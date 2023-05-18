package com.website.enbookingbe.quiz.exception;

import com.website.enbookingbe.exception.NotFoundException;

public class QuizCardNotFoundException extends NotFoundException {

    public QuizCardNotFoundException(Integer quizId, Integer cardId) {
        super("Quiz card not found with quiz id: %d and card id: %d", quizId, cardId);
    }
}
