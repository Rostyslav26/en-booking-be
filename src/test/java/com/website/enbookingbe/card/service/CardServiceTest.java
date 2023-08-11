package com.website.enbookingbe.card.service;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.repository.CardRepository;
import com.website.enbookingbe.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.website.enbookingbe.card.CardTestBuilder.getCard;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @Test
    void testCreateNewCard() {
        final CardV2 card = getCard(LocalDateTime.now(), "question", "answer");

        when(cardRepository.save(any())).thenReturn(card);

        final CardV2 actual = cardService.createNewCard(card.getQuestion(), card.getAnswer(), card.getAuthorId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(card);
    }

    @Test
    void testGetById() {
        final Integer cardId = 1;
        final CardV2 card = getCard(cardId, LocalDateTime.now(), "question", "answer");

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        final CardV2 actual = cardService.getById(cardId);

        assertThat(actual).usingRecursiveComparison().isEqualTo(card);
    }

    @Test
    void testGetById_shouldThrowNotFoundException_whenCardNotFound() {
        when(cardRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cardService.getById(1))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Not found card with id %s ", 1);
    }

    @Test
    void testFindAllByIds() {
        final CardV2 card1 = getCard(1, LocalDateTime.now(), "question1", "answer1");
        final CardV2 card2 = getCard(2, LocalDateTime.now(), "question2", "answer2");
        List<CardV2> expected = List.of(card1, card2);

        when(cardRepository.findAllByIds(List.of(1, 2))).thenReturn(expected);

        final List<CardV2> actual = cardService.getByIds(List.of(1, 2));

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}