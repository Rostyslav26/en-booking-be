package com.website.enbookingbe.card.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.website.enbookingbe.DBTest;
import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.domain.UserCard;
import com.website.enbookingbe.utils.PageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.website.enbookingbe.data.jooq.tables.UserCard.USER_CARD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DBTest
class UserCardRepositoryTest {

    @Autowired
    private UserCardRepository userCardRepository;

    @Test
    @DataSet(value = {"datasets/user-card/cards.xml", "datasets/user/users.xml"})
    @ExpectedDataSet(value = "datasets/user-card/expected-stored-user-card.xml")
    void testSave() {
        final UserCard userCard = UserCard.builder()
            .userId(1)
            .card(CardV2.builder().id(1).build())
            .build();

        UserCard actual = userCardRepository.save(userCard);

        assertThat(actual).isNotNull();
        assertThat(actual.getUserId()).isEqualTo(1);
        assertThat(actual.getCard().getId()).isEqualTo(1);
        assertThat(actual.isFavorite()).isFalse();
        assertThat(actual.isLearned()).isFalse();
    }

    @Test
    @DataSet(value = {"datasets/user-card/cards.xml", "datasets/user/users.xml", "datasets/user-card/user-cards.xml"})
    @ExpectedDataSet(value = "datasets/user-card/expected-updated-user-card.xml")
    void testUpdate() {
        final UserCard userCard = UserCard.builder()
            .userId(1)
            .card(CardV2.builder().id(1).build())
            .favorite(false)
            .learned(false)
            .build();

        userCardRepository.update(userCard, USER_CARD.FAVORITE);
    }

    @Test
    @DataSet(value = {"datasets/user-card/cards.xml", "datasets/user/users.xml", "datasets/user-card/user-cards.xml"})
    void testFind() {
        final Optional<UserCard> userCardOpt = userCardRepository.findById(1, 1);

        assertTrue(userCardOpt.isPresent());

        final UserCard userCard = userCardOpt.get();
        assertThat(userCard).usingRecursiveComparison().isEqualTo(getExpectedUserCard());
    }

    @Test
    @DataSet(value = {"datasets/user-card/cards.xml", "datasets/user/users.xml", "datasets/user-card/user-cards.xml"})
    void testFindAllByUserIdAndCardIds() {
        final List<UserCard> result = userCardRepository.findAllByUserIdAndCardIds(1, List.of(1, 1));

        assertThat(result).hasSize(1);
    }

    @Test
    @DataSet(value = {"datasets/user-card/cards.xml", "datasets/user/users.xml", "datasets/user-card/user-cards.xml"})
    void testFindNotLearnedByUserId() {
        final Integer userId = 1;
        final PageRequest pageRequest = PageRequest.of(1, 10);

        final List<UserCard> result = userCardRepository.findNotLearnedByUserId(userId, pageRequest);

        assertThat(result).hasSize(2);
    }

    @Test
    @DataSet(value = {"datasets/user-card/cards.xml", "datasets/user/users.xml", "datasets/user-card/user-cards.xml"})
    @ExpectedDataSet(value = "datasets/user-card/expected-removed-user-card.xml")
    void testRemove() {
        userCardRepository.remove(1, 2);
    }

    private UserCard getExpectedUserCard() {
        final CardV2 card = CardV2.builder()
            .id(1)
            .question("question1")
            .answer("answer1")
            .authorId(1)
            .createdAt(LocalDateTime.of(2019, 1, 1, 0, 0, 0))
            .updatedAt(LocalDateTime.of(2019, 1, 1, 0, 0, 0))
            .build();

        return UserCard.builder()
            .userId(1)
            .card(card)
            .favorite(true)
            .learned(false)
            .build();
    }
}