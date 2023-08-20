package com.website.enbookingbe.card.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.website.enbookingbe.DBTest;
import com.website.enbookingbe.card.domain.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBTest
class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;

    @Test
    @DataSet("datasets/user/users.xml")
    @ExpectedDataSet(value = "datasets/card/expected-stored-card.xml", ignoreCols = {"created_at", "updated_at"})
    void testSaveCard() {
        Card card = Card.builder()
            .question("question")
            .answer("answer")
            .authorId(1)
            .build();

        Card actual = cardRepository.save(card);
        assertEquals(1, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());
    }

    @Test
    @DataSet(value = {"datasets/user/users.xml", "datasets/card/cards.xml"})
    @ExpectedDataSet(value = "datasets/card/expected-updated-card.xml", ignoreCols = {"created_at", "updated_at"})
    void testUpdateCard() {
        Card card = Card.builder()
            .id(1)
            .question("updated question")
            .answer("updated answer")
            .authorId(1)
            .build();

        cardRepository.update(card);
    }

    @Test
    @DataSet(value = {"datasets/user/users.xml", "datasets/card/cards.xml"})
    void testFindById() {
        Card card = cardRepository.findById(1).orElse(null);

        assertNotNull(card);
        assertThat(card).usingRecursiveComparison().isEqualTo(getExpectedCard(1));
    }

    @Test
    @DataSet(value = {"datasets/user/users.xml", "datasets/card/cards.xml"})
    void testFindAllByIds() {
        List<Card> cards = cardRepository.findAllByIds(List.of(1, 2));

        assertThat(cards).usingRecursiveComparison().isEqualTo(List.of(getExpectedCard(1), getExpectedCard(2)));
    }

    private Card getExpectedCard(Integer id) {
        Card card = Card.builder()
            .id(id)
            .question("question" + id)
            .answer("answer" + id)
            .authorId(1)
            .build();

        LocalDateTime dateTime = LocalDateTime.of(2019, 1, 1, 0, 0, 0);
        card.setCreatedAt(dateTime);
        card.setUpdatedAt(dateTime);

        return card;
    }
}