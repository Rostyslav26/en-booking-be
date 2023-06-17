package com.website.enbookingbe.card.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.website.enbookingbe.DBTest;
import com.website.enbookingbe.card.CardV2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBTest
class CardRepositoryV2Test {

    @Autowired
    private CardRepositoryV2 cardRepositoryV2;

    @Test
    @DataSet("datasets/user/users.xml")
    @ExpectedDataSet(value = "datasets/card/expected-stored-card.xml", ignoreCols = {"created_at", "updated_at"})
    void testSaveCard() {
        CardV2 card = new CardV2();
        card.setQuestion("question");
        card.setAnswer("answer");
        card.setAuthorId(1);

        CardV2 actual = cardRepositoryV2.save(card);
        assertEquals(1, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());
    }

    @Test
    @DataSet(value = {"datasets/user/users.xml", "datasets/card/cards.xml"})
    void testFindById() {
        CardV2 card = cardRepositoryV2.findById(1).orElse(null);

        assertNotNull(card);
        assertThat(card).usingRecursiveComparison().isEqualTo(getExpectedCard(1));
    }

    @Test
    @DataSet(value = {"datasets/user/users.xml", "datasets/card/cards.xml"})
    void testFindAllByIds() {
        List<CardV2> cards = cardRepositoryV2.findAllByIds(List.of(1, 2));

        assertThat(cards).usingRecursiveComparison().isEqualTo(List.of(getExpectedCard(1), getExpectedCard(2)));
    }

    private CardV2 getExpectedCard(Integer id) {
        CardV2 card = new CardV2();
        card.setId(id);
        card.setQuestion("question" + id);
        card.setAnswer("answer" + id);
        card.setAuthorId(1);

        LocalDateTime dateTime = LocalDateTime.of(2019, 1, 1, 0, 0, 0);
        card.setCreatedAt(dateTime);
        card.setUpdatedAt(dateTime);

        return card;
    }
}