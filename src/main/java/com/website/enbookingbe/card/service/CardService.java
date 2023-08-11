package com.website.enbookingbe.card.service;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.repository.CardRepository;
import com.website.enbookingbe.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public CardV2 createNewCard(String question, String answer, Integer authorId) {
        final CardV2 card = CardV2.builder()
            .question(question)
            .answer(answer)
            .authorId(authorId)
            .build();

        return cardRepository.save(card);
    }

    public CardV2 getById(Integer id) {
        return cardRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found card with id %s ", id));
    }

    public List<CardV2> getByIds(List<Integer> cardIds) {
        return cardRepository.findAllByIds(cardIds);
    }

    public CardV2 update(CardV2 card) {
        return cardRepository.update(card);
    }
}
