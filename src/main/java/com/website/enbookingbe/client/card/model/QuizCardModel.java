package com.website.enbookingbe.client.card.model;

public record QuizCardModel (
    Integer cardId,
    String question,
    String answer,
    String prompt
) {
}
