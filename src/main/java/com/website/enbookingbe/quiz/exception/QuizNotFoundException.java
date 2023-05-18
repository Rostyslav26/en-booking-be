package com.website.enbookingbe.quiz.exception;

import com.website.enbookingbe.exception.NotFoundException;

public class QuizNotFoundException extends NotFoundException {

    public QuizNotFoundException(Integer id) {
        super("Quiz not found with id: %d", id);
    }
}
