package com.website.enbookingbe.card.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.website.enbookingbe.DBTest;
import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.domain.UserCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DBTest
class UserCardRepositoryV2Test {

    @Autowired
    private UserCardRepositoryV2 userCardRepositoryV2;

    @Test
    @DataSet(value = {"datasets/user-card/cards.xml", "datasets/user/users.xml"})
    @ExpectedDataSet(value = "datasets/user-card/expected-stored-user-card.xml")
    void testSaveUserCard() {
        final UserCard userCard = UserCard.builder()
                .userId(1)
                .card(CardV2.builder().id(1).build())
                .build();

        userCardRepositoryV2.save(userCard);
    }

    @Test
    @DataSet(value = {"datasets/user-card/cards.xml", "datasets/user/users.xml", "datasets/user-card/user-cards.xml"})
    @ExpectedDataSet(value = "datasets/user-card/expected-removed-user-card.xml")
    void testRemoveUserCard() {
        userCardRepositoryV2.remove(1, 2);
    }
}