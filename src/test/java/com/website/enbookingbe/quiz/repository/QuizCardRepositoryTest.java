package com.website.enbookingbe.quiz.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.website.enbookingbe.DBTest;
import com.website.enbookingbe.quiz.domain.QuizCard;
import com.website.enbookingbe.quiz.domain.QuizCardStatus;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.website.enbookingbe.data.jooq.Tables.QUIZ_CARD;
import static org.assertj.core.api.Assertions.assertThat;

@DBTest
class QuizCardRepositoryTest {

    @Autowired
    private QuizCardRepository quizCardRepository;

    @Autowired
    private DSLContext dslContext;

    @Test
    @DataSet(value = {"datasets/quiz/quiz.xml", "datasets/card/cards.xml", "datasets/user/users.xml"})
    @ExpectedDataSet(value = "datasets/quiz-card/quiz-cards.xml")
    void testSaveAll() {
        final List<QuizCard> quizCards = List.of(
            QuizCard.builder()
                .quizId(1)
                .cardId(1)
                .status(QuizCardStatus.COMPLETED)
                .build(),
            QuizCard.builder()
                .quizId(1)
                .cardId(2)
                .status(QuizCardStatus.NEW)
                .build()
        );

        quizCardRepository.saveAll(quizCards);

        final int count = dslContext.fetchCount(QUIZ_CARD, QUIZ_CARD.QUIZ_ID.eq(1));

        assertThat(count).isEqualTo(2);
    }

    @Test
    @DataSet(value = "datasets/quiz-card/quiz-cards.xml", disableConstraints = true)
    void testFindAllByQuizId() {
        final List<QuizCard> quizCards = quizCardRepository.findAllByQuizId(1);

        assertThat(quizCards).hasSize(2);
    }

    @Test
    @DataSet(value = "datasets/quiz-card/quiz-cards.xml", disableConstraints = true)
    void testFindOne() {
        final QuizCard quizCard = quizCardRepository.findOne(1, 1).orElse(null);

        assertThat(quizCard).isNotNull();
        assertThat(quizCard.getQuizId()).isEqualTo(1);
        assertThat(quizCard.getCardId()).isEqualTo(1);
        assertThat(quizCard.getStatus()).isEqualTo(QuizCardStatus.COMPLETED);
    }

    @Test
    @DataSet(value = "datasets/quiz-card/quiz-cards.xml", disableConstraints = true)
    void testFindNotCompleted() {
        final List<QuizCard> quizCards = quizCardRepository.findNotCompleted(1);

        assertThat(quizCards).hasSize(1);
    }
}