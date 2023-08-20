package com.website.enbookingbe.card.service;

import com.website.enbookingbe.card.domain.Card;
import com.website.enbookingbe.card.repository.CardRepository;
import com.website.enbookingbe.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public Card createNewCard(String question, String answer, Integer authorId) {
        final Card card = Card.builder()
            .question(question)
            .answer(answer)
            .authorId(authorId)
            .build();

        return cardRepository.save(card);
    }

    public Card getById(Integer id) {
        return cardRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found card with id %s ", id));
    }

    public List<Card> getByIds(List<Integer> cardIds) {
        return cardRepository.findAllByIds(cardIds);
    }

    public Card update(Card card) {
        return cardRepository.update(card);
    }
}
