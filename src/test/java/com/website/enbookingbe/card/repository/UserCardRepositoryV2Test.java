package com.website.enbookingbe.card.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.website.enbookingbe.DBTest;
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
        userCardRepositoryV2.save(1, 1);
    }
}