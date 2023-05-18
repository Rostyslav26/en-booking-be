package com.website.enbookingbe.quiz;

import com.website.enbookingbe.quiz.entity.Quiz;
import com.website.enbookingbe.quiz.resource.QuizResource;

public class QuizMapper {

    public QuizResource toResource(Quiz quiz, Integer userId) {
        return new QuizResource(
            quiz.getId(),
            userId,
            quiz.getStatus(),
            quiz.getCreatedAt(),
            quiz.getUpdatedAt()
        );
    }
}
