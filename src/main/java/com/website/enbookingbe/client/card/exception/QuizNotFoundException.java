package com.website.enbookingbe.client.card.exception;

import com.website.enbookingbe.core.exception.NotFoundException;

public class QuizNotFoundException extends NotFoundException {

    public QuizNotFoundException(Integer id) {
        super("Quiz not found with id: %d", id);
    }
}
