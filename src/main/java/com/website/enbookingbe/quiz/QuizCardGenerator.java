package com.website.enbookingbe.quiz;

import com.website.enbookingbe.card.Card;
import com.website.enbookingbe.card.CardService;
import com.website.enbookingbe.quiz.entity.QuizCard;
import com.website.enbookingbe.quiz.model.QuizCardStatus;
import com.website.enbookingbe.quiz.resource.QuizCardResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuizCardGenerator {
    private final CardService cardService;

    public List<QuizCardResource> prepareQuizCardsToLearn(Collection<QuizCard> quizCards) {
        quizCards.forEach(qc -> qc.setStatus(QuizCardStatus.IN_PROGRESS));

        final List<Integer> cardIds = quizCards.stream()
            .map(qz -> qz.getId().getCardId())
            .collect(Collectors.toList());

        final List<Card> cards = cardService.getByIds(cardIds);
        Collections.shuffle(cards);

        return cards.stream()
            .map(this::toQuizCardResource)
            .collect(Collectors.toList());
    }

    private QuizCardResource toQuizCardResource(Card card) {
        final String prompt = PromptGenerator.generatePrompt(card.getAnswer());

        return new QuizCardResource(card.getId(), card.getQuestion(), card.getAnswer(), prompt);
    }
}