package com.website.enbookingbe.client.card.service;

import com.website.enbookingbe.client.card.domain.Card;
import com.website.enbookingbe.client.card.exception.CardNotFoundException;
import com.website.enbookingbe.client.card.model.request.CreateCardRequest;
import com.website.enbookingbe.client.card.model.request.UpdateCardRequest;
import com.website.enbookingbe.client.card.repository.CardRepository;
import com.website.enbookingbe.client.card.repository.UserCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {
    private final CardRepository cardRepository;
    private final UserCardRepository userCardRepository;

    public Card create(CreateCardRequest dto, Integer userId) {
        final Card card = prepareCard(dto.getQuestion(), dto.getAnswer(), userId);
        final Card savedCard = cardRepository.save(card);
        userCardRepository.save(savedCard.getId(), userId);

        return savedCard;
    }

    public Card addToUserCollection(Integer cardId, Integer userId) {
        final Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException(cardId));
        Card newCard = prepareCard(card.getQuestion(), card.getAnswer(), card.getAuthorId());

        newCard = cardRepository.save(newCard);
        userCardRepository.save(newCard.getId(), userId);

        return newCard;
    }

    public void deleteFromUserCollection(Integer cardId, Integer userId) {
        userCardRepository.removeById(cardId, userId);
    }

    public Card update(UpdateCardRequest dto, Integer userId) {
        final Card card = userCardRepository.findOne(userId, dto.getId()).orElseThrow(() -> new CardNotFoundException(dto.getId()));
        card.setAnswer(dto.getAnswer());
        card.setQuestion(dto.getQuestion());

        return cardRepository.update(card, Set.of(CARD.QUESTION, CARD.ANSWER));
    }

    public void markAsFavorite(Integer cardId, boolean favorite, Integer userId) {
        final Card card = userCardRepository.findOne(userId, cardId).orElseThrow(() -> new CardNotFoundException(cardId));
        card.setFavorite(favorite);

        cardRepository.update(card, Set.of(CARD.FAVORITE));
    }

    public void markAsLearned(Integer cardId, boolean learned, Integer userId) {
        final Card card = userCardRepository.findOne(userId, cardId).orElseThrow(() -> new CardNotFoundException(cardId));
        card.setLearned(learned);

        cardRepository.update(card);
    }

    @Transactional(readOnly = true)
    public List<Card> getNotLearnedByUserId(Integer userId, Integer limit) {
        return userCardRepository.findNotLearned(userId, limit);
    }

    @Transactional(readOnly = true)
    public List<Card> getByUserId(Integer userId) {
        return userCardRepository.findAll(userId);
    }

    @Transactional(readOnly = true)
    public Optional<Card> getById(Integer id) {
        return cardRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Card> getByIds(List<Integer> ids) {
        return cardRepository.findByIds(ids);
    }

    private Card prepareCard(String question, String answer, Integer authorId) {
        final Card card = new Card();
        card.setQuestion(question);
        card.setAnswer(answer);
        card.setAuthorId(authorId);

        return card;
    }
}
