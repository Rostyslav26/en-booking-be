package com.website.enbookingbe.quiz.service;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.service.CardService;
import com.website.enbookingbe.quiz.domain.QuizCard;
import com.website.enbookingbe.quiz.domain.QuizCardStatus;
import com.website.enbookingbe.quiz.resource.QuizCardResource;
import com.website.enbookingbe.utils.PromptGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuizCardGenerator {
    private final CardService cardService;

    public List<QuizCardResource> prepareQuizCardsToLearn(List<QuizCard> quizCards) {
        quizCards.forEach(this::markAsInProgress);

        final List<Integer> cardIds = quizCards.stream()
            .map(QuizCard::getCardId)
            .toList();

        final List<CardV2> cards = cardService.getByIds(cardIds);
        Collections.shuffle(cards);

        return cards.stream()
            .map(this::toQuizCardResource)
            .toList();
    }

    private void markAsInProgress(QuizCard quizCard) {
        quizCard.setStatus(QuizCardStatus.COMPLETED);
    }

    private QuizCardResource toQuizCardResource(CardV2 card) {
        final String prompt = PromptGenerator.generatePrompt(card.getAnswer());

        return new QuizCardResource(card.getId(), card.getQuestion(), card.getAnswer(), prompt);
    }
}