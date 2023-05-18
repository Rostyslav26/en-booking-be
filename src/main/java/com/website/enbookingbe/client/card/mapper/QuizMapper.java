package com.website.enbookingbe.client.card.mapper;

import com.website.enbookingbe.client.card.entity.Quiz;
import com.website.enbookingbe.client.card.resource.QuizResource;

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
