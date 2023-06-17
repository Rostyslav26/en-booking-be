package com.website.enbookingbe.card.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.website.enbookingbe.DBTest;
import com.website.enbookingbe.card.CardV2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DBTest
class CardRepositoryV2Test {

    @Autowired
    private CardRepositoryV2 cardRepositoryV2;

    @Test
    @DataSet("datasets/card/author.xml")
    @ExpectedDataSet("datasets/card/expected-stored-card.xml")
    void testSaveCard() {
        CardV2 card = new CardV2();
        card.setQuestion("question");
        card.setAnswer("answer");
        card.setAuthorId(1);

        CardV2 actual = cardRepositoryV2.save(card);
        assertEquals(1, actual.getId());
    }
}