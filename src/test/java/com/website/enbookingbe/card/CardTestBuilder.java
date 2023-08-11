package com.website.enbookingbe.card;

import com.website.enbookingbe.card.domain.CardV2;

import java.time.LocalDateTime;

public class CardTestBuilder {

    private CardTestBuilder() {
    }

    public static CardV2 getCard(LocalDateTime createdAt, String question, String answer) {
        return CardV2.builder()
            .id(1)
            .createdAt(createdAt)
            .updatedAt(createdAt)
            .question(question)
            .authorId(1)
            .answer(answer)
            .build();
    }

    public static CardV2 getCard(int cardId, LocalDateTime createdAt, String question, String answer) {
        return CardV2.builder()
            .id(cardId)
            .createdAt(createdAt)
            .updatedAt(createdAt)
            .question(question)
            .authorId(1)
            .answer(answer)
            .build();
    }
}
