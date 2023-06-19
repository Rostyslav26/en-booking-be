package com.website.enbookingbe.card.service;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.repository.CardRepositoryV2;
import com.website.enbookingbe.card.resource.CreateCardResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceV2Test {

    @Mock
    private CardRepositoryV2 cardRepository;

    @Mock
    private UserCardService userCardService;

    @InjectMocks
    private CardServiceV2 cardServiceV2;

    @Test
    void testCreate() {
        final CardV2 expected = getCard(LocalDateTime.now());
        when(cardRepository.save(any())).thenReturn(expected);

        final CardV2 actual = cardServiceV2.create(getCreateCardResource(), 1);

        verify(userCardService).addCardToUser(1, expected);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private CreateCardResource getCreateCardResource() {
        return new CreateCardResource("question", "answer");
    }

    private CardV2 getCard(LocalDateTime date) {
        return CardV2.builder()
            .id(1)
            .question("question")
            .answer("answer")
            .authorId(1)
            .createdAt(date)
            .updatedAt(date)
            .build();
    }
}