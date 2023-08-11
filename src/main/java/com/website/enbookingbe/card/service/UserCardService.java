package com.website.enbookingbe.card.service;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.domain.UserCard;
import com.website.enbookingbe.card.repository.UserCardRepository;
import com.website.enbookingbe.card.resource.CreateCardResource;
import com.website.enbookingbe.card.resource.UpdateCardResource;
import com.website.enbookingbe.exception.NotFoundException;
import com.website.enbookingbe.utils.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.website.enbookingbe.data.jooq.tables.UserCard.USER_CARD;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCardService {
    private final UserCardRepository userCardRepository;
    private final CardService cardService;

    public UserCard create(CreateCardResource dto, Integer userId) {
        final CardV2 card = cardService.createNewCard(dto.question(), dto.answer(), userId);

        final UserCard userCard = UserCard.builder()
            .card(card)
            .userId(userId)
            .build();

        return userCardRepository.save(userCard);
    }

    public UserCard update(Integer cardId, UpdateCardResource dto, Integer userId) {
        final UserCard userCard = getUserCard(userId, cardId);

        final CardV2 card = userCard.getCard();
        card.setAnswer(dto.answer());
        card.setQuestion(dto.question());

        CardV2 updatedCard = cardService.update(card);
        userCard.setCard(updatedCard);

        return userCard;
    }

    public List<UserCard> getNotLearnedCards(Integer userId, PageRequest pageRequest) {
        return userCardRepository.findNotLearnedByUserId(userId, pageRequest);
    }

    public void remove(Integer cardId, Integer userId) {
        userCardRepository.remove(userId, cardId);
    }

    public void markAsFavorite(Integer id, boolean favorite, Integer cardId) {
        UserCard userCard = getUserCard(id, cardId);
        userCard.setFavorite(favorite);

        userCardRepository.update(userCard, USER_CARD.FAVORITE);
    }

    public void markAsLearned(Integer cardId, Integer userId, boolean learned) {
        UserCard userCard = getUserCard(userId, cardId);
        userCard.setLearned(learned);

        userCardRepository.update(userCard, USER_CARD.LEARNED);
    }

    public void copyCardToUser(Integer cardId, Integer userId) {
        CardV2 card = cardService.getById(cardId);

        CardV2 copiedCard = cardService.createNewCard(card.getQuestion(), card.getAnswer(), userId);

        final UserCard userCard = UserCard.builder()
            .card(copiedCard)
            .userId(userId)
            .build();

        userCardRepository.save(userCard);
    }

    @Transactional(readOnly = true)
    public List<UserCard> getUserCards(Integer userId, List<Integer> cardIds) {
        return userCardRepository.findAllByUserIdAndCardIds(userId, cardIds);
    }

    @Transactional(readOnly = true)
    public UserCard getUserCard(Integer userId, Integer cardId) {
        return userCardRepository.findById(userId, cardId)
            .orElseThrow(() -> new NotFoundException("Card not found by id: %s ", cardId));
    }

    @Transactional(readOnly = true)
    public List<UserCard> getAllByUserId(Integer userId) {
        return userCardRepository.findAllByUserId(userId);
    }
}
