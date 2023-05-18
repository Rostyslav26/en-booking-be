package com.website.enbookingbe.quiz.model;

public record QuizCardResource(
    Integer cardId,
    String question,
    String answer,
    String prompt
) {
}
