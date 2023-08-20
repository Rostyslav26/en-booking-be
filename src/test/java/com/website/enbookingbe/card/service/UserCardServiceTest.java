package com.website.enbookingbe.card.service;

import com.website.enbookingbe.card.domain.Card;
import com.website.enbookingbe.card.domain.UserCard;
import com.website.enbookingbe.card.repository.UserCardRepository;
import com.website.enbookingbe.card.resource.CreateCardResource;
import com.website.enbookingbe.card.resource.UpdateCardResource;
import com.website.enbookingbe.exception.NotFoundException;
import com.website.enbookingbe.utils.PageRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.website.enbookingbe.card.CardTestBuilder.getCard;
import static com.website.enbookingbe.data.jooq.Tables.USER_CARD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCardServiceTest {

    @Mock
    private UserCardRepository userCardRepository;

    @Mock
    private CardService cardService;

    @InjectMocks
    private UserCardService userCardService;

    @Test
    void testCreate() {
        final Card card = getCard(LocalDateTime.now(), "question", "answer");
        final CreateCardResource dto = new CreateCardResource("question", "answer");
        final Integer userId = 1;

        when(cardService.createNewCard(any(), any(), any())).thenReturn(card);
        when(userCardRepository.save(any())).thenReturn(UserCard.builder().userId(userId).card(card).build());

        final UserCard actual = userCardService.create(dto, userId);

        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(UserCard.builder().userId(userId).card(card).build());
    }

    @Test
    void testUpdate() {
        LocalDateTime dateTime = LocalDateTime.now();

        final Integer userId = 1;
        final Integer cardId = 1;

        final Card card = getCard(dateTime, "question", "answer");
        UserCard expected = getUserCard(userId, card);
        when(userCardRepository.findById(userId, cardId)).thenReturn(Optional.of(expected));
        when(cardService.update(any())).thenReturn(card);

        final UserCard actual = userCardService.update(
            cardId,
            new UpdateCardResource("newQuestion", "newAnswer"),
            userId
        );

        expected.setCard(card);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testUpdate_shouldThrowNotFoundException_whenUserCardNotFound() {
        final Integer userId = 1;
        final Integer cardId = 1;

        when(userCardRepository.findById(userId, cardId)).thenReturn(Optional.empty());

        UpdateCardResource updateCardResource = new UpdateCardResource("newQuestion", "newAnswer");

        assertThatThrownBy(() -> userCardService.update(cardId, updateCardResource, userId))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void testCopyCardToUser() {
        final Integer userId = 1;
        final Integer cardIdForCopy = 1;

        final Card card = getCard(LocalDateTime.now(), "question", "answer");
        final UserCard userCard = getUserCard(userId, card);

        final ArgumentCaptor<UserCard> userCardCaptor = ArgumentCaptor.forClass(UserCard.class);

        final Card expectedCard = getCard(2, LocalDateTime.now(), "question", "answer");
        when(cardService.getById(cardIdForCopy)).thenReturn(card);
        when(cardService.createNewCard(any(), any(), any()))
            .thenReturn(expectedCard);
        when(userCardRepository.save(userCardCaptor.capture())).thenReturn(userCard);

        userCardService.copyCardToUser(cardIdForCopy, userId);

        verify(userCardRepository).save(userCardCaptor.capture());
        assertThat(userCardCaptor.getValue().getCard()).isEqualTo(expectedCard);
    }

    @Test
    void testCopyCardToUser_ShouldThrowNotFoundException_WhenCardNotFound() {
        final Integer userId = 1;
        final Integer cardId = 1;

        when(cardService.getById(cardId)).thenThrow(new NotFoundException("Card not found"));

        assertThatThrownBy(() -> userCardService.copyCardToUser(cardId, userId))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void testMarkAsFavorite() {
        Integer userId = 1;
        Integer cardId = 1;
        boolean favorite = true;

        UserCard userCard = getUserCard(userId, getCard(LocalDateTime.now(), "Card 1", "Card 1"));
        when(userCardRepository.findById(userId, cardId)).thenReturn(Optional.of(userCard));

        userCardService.markAsFavorite(userId, favorite, cardId);

        verify(userCardRepository).update(userCard, USER_CARD.FAVORITE);
        assertThat(userCard.isFavorite()).isTrue();
    }

    @Test
    void testMarkAsFavorite_ShouldNotUpdateUserCard_WhenUserCardNotFound() {
        Integer id = 1;
        Integer cardId = 1;
        boolean favorite = true;

        when(userCardRepository.findById(id, cardId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userCardService.markAsFavorite(id, favorite, cardId))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void testMarkAsLearned() {
        Integer userId = 1;
        Integer cardId = 1;
        boolean learned = true;

        UserCard userCard = getUserCard(userId, getCard(LocalDateTime.now(), "Card 1", "Card 1"));
        when(userCardRepository.findById(userId, cardId)).thenReturn(Optional.of(userCard));

        userCardService.markAsLearned(cardId, userId, learned);

        verify(userCardRepository).update(userCard, USER_CARD.LEARNED);
        assertThat(userCard.isLearned()).isTrue();
    }

    @Test
    void testMarkAsLearned_ShouldNotUpdateUserCard_WhenUserCardNotFound() {
        Integer id = 1;
        Integer cardId = 1;
        boolean learned = true;

        when(userCardRepository.findById(id, cardId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userCardService.markAsLearned(cardId, id, learned))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void testRemove() {
        final Integer userId = 1;
        final Integer cardId = 1;

        userCardService.remove(userId, cardId);

        verify(userCardRepository).remove(userId, cardId);
    }

    @Test
    void testGetAllByUserId() {
        Card card = getCard(LocalDateTime.now(), "question", "answer");
        final Integer userId = 1;

        final List<UserCard> expected = List.of(UserCard.builder().userId(userId).card(card).build());

        when(userCardRepository.findAllByUserId(userId)).thenReturn(expected);

        List<UserCard> actual = userCardService.getAllByUserId(userId);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testGetUserCards() {
        Integer userId = 1;
        List<Integer> cardIds = List.of(1, 2, 3);

        Card card1 = getCard(1, LocalDateTime.now(), "question1", "answer1");
        Card card2 = getCard(2, LocalDateTime.now(), "question2", "answer2");
        Card card3 = getCard(3, LocalDateTime.now(), "question3", "answer3");

        List<UserCard> expected = List.of(
            getUserCard(userId, card1),
            getUserCard(userId, card2),
            getUserCard(userId, card3)
        );

        when(userCardRepository.findAllByUserIdAndCardIds(userId, cardIds)).thenReturn(expected);

        List<UserCard> actual = userCardService.getUserCards(userId, cardIds);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testGetNotLearnedCards() {
        Integer userId = 1;
        PageRequest pageRequest = PageRequest.of(1, 10);

        UserCard userCard = getUserCard(userId, getCard(LocalDateTime.now(), "Card 1", "Card 1"));
        List<UserCard> expectedCards = new ArrayList<>();
        expectedCards.add(userCard);

        when(userCardRepository.findNotLearnedByUserId(userId, pageRequest))
            .thenReturn(expectedCards);

        List<UserCard> actual = userCardService.getNotLearnedCards(userId, pageRequest);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedCards);
    }

    private UserCard getUserCard(Integer userId, Card card) {
        return UserCard.builder()
            .userId(userId)
            .card(card)
            .build();
    }
}