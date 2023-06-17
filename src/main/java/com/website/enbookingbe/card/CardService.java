package com.website.enbookingbe.card;

import com.website.enbookingbe.card.repository.CardRepository;
import com.website.enbookingbe.card.repository.UserCardRepository;
import com.website.enbookingbe.card.resource.CreateCardResource;
import com.website.enbookingbe.card.resource.UpdateCardResource;
import com.website.enbookingbe.management.entity.User;
import com.website.enbookingbe.management.repository.UserRepository;
import com.website.enbookingbe.quiz.entity.UserCard;
import com.website.enbookingbe.quiz.entity.UserCardId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;

    public Card create(CreateCardResource dto, Integer userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        final Card card = createNewCard(dto.question(), dto.answer());
        user.addCard(card);

        return card;
    }

    public void addToUserCollection(Integer cardId, Integer userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        final Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException(cardId));

        user.addToCardCollection(card);
    }

    public void deleteFromUserCollection(Integer cardId, Integer userId) {
        final UserCardId userCardId = new UserCardId(userId, cardId);
        userCardRepository.deleteById(userCardId);
    }

    public Card update(UpdateCardResource dto, Integer userId) {
        final UserCard userCard = getUserCard(dto.id(), userId);

        final Card card = userCard.getCard();
        card.setAnswer(dto.answer());
        card.setQuestion(dto.question());

        return card;
    }

    public void markAsFavorite(Integer cardId, boolean favorite, Integer userId) {
        final UserCard userCard = getUserCard(cardId, userId);

        userCard.setFavorite(favorite);
    }

    public void markAsLearned(Integer cardId, boolean learned, Integer userId) {
        final UserCard userCard = getUserCard(cardId, userId);

        userCard.setLearned(learned);
    }

    @Transactional(readOnly = true)
    public List<Card> getNotLearnedByUserId(Integer userId, Integer limit) {
        final PageRequest pageRequest = PageRequest.ofSize(limit);

        return userCardRepository.findNotLearnedByUserId(userId, pageRequest).stream()
            .map(UserCard::getCard)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<Card> getByUserId(Integer userId) {
        return userCardRepository.findByUserId(userId).stream()
            .map(UserCard::getCard)
            .toList();
    }

    @Transactional(readOnly = true)
    public Optional<Card> getById(Integer id) {
        return cardRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Card> getByIds(List<Integer> ids) {
        return cardRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public List<Card> getUserCards(Integer userId, List<Integer> cardIds) {
        return userCardRepository.findByUserIdAndCardIdIn(userId, cardIds).stream()
            .map(UserCard::getCard)
            .toList();
    }

    private Card createNewCard(String question, String answer) {
        final Card card = new Card();
        card.setQuestion(question);
        card.setAnswer(answer);

        return card;
    }

    private UserCard getUserCard(Integer cardId, Integer userId) {
        final UserCardId userCardId = new UserCardId(userId, cardId);
        return userCardRepository.findById(userCardId).orElseThrow(() -> new CardNotFoundException(cardId));
    }
}
