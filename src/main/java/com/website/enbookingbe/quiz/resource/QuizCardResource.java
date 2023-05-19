package com.website.enbookingbe.quiz.resource;

public record QuizCardResource(
    Integer cardId,
    String question,
    String answer,
    String prompt
) {
}
