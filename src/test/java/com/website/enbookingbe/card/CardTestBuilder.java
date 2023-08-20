package com.website.enbookingbe.card;

import com.website.enbookingbe.card.domain.Card;

import java.time.LocalDateTime;

public class CardTestBuilder {

    private CardTestBuilder() {
    }

    public static Card getCard(LocalDateTime createdAt, String question, String answer) {
        return Card.builder()
            .id(1)
            .createdAt(createdAt)
            .updatedAt(createdAt)
            .question(question)
            .authorId(1)
            .answer(answer)
            .build();
    }

    public static Card getCard(int cardId, LocalDateTime createdAt, String question, String answer) {
        return Card.builder()
            .id(cardId)
            .createdAt(createdAt)
            .updatedAt(createdAt)
            .question(question)
            .authorId(1)
            .answer(answer)
            .build();
    }
}
