package com.website.enbookingbe.quiz.converter;

import com.website.enbookingbe.quiz.domain.Quiz;
import com.website.enbookingbe.quiz.resource.QuizResource;

public class QuizResourceConverter {

    private QuizResourceConverter() {
    }

    public static QuizResource toResource(Quiz quiz, Integer userId) {
        return new QuizResource(
            quiz.getId(),
            userId,
            quiz.getStatus(),
            quiz.getCreatedAt(),
            quiz.getUpdatedAt()
        );
    }
}
