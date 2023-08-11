package com.website.enbookingbe.quiz;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.domain.UserCard;
import com.website.enbookingbe.card.service.UserCardService;
import com.website.enbookingbe.exception.NotFoundException;
import com.website.enbookingbe.quiz.domain.Quiz;
import com.website.enbookingbe.quiz.domain.QuizCard;
import com.website.enbookingbe.quiz.domain.QuizCardStatus;
import com.website.enbookingbe.quiz.domain.QuizStatus;
import com.website.enbookingbe.quiz.repository.QuizCardRepository;
import com.website.enbookingbe.quiz.repository.QuizRepository;
import com.website.enbookingbe.utils.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {
    private final UserCardService userCardService;
    private final QuizRepository quizRepository;
    private final QuizCardRepository quizCardRepository;

    @Transactional(readOnly = true)
    public Quiz getQuizById(Integer quizId, Integer userId) {
        return quizRepository.findOne(quizId, userId)
            .orElseThrow(() -> new NotFoundException("Quiz not found by id: %s", quizId));
    }

    public Quiz createQuiz(List<Integer> cardIds, Integer userId) {
        final List<UserCard> userCards = userCardService.getUserCards(userId, cardIds);

        return createQuiz(userId, userCards);
    }

    public Quiz createQuizByNotLearnedCards(Integer userId, Integer limit) {
        final List<UserCard> userCards = userCardService.getNotLearnedCards(userId, PageRequest.of(1, limit));

        return createQuiz(userId, userCards);
    }

    private Quiz createQuiz(Integer userId, List<UserCard> userCards) {
        checkState(!userCards.isEmpty(), "Quiz cannot be created because it has no cards");

        final Quiz quiz = Quiz.builder()
            .userId(userId)
            .status(QuizStatus.CREATED)
            .createdAt(LocalDateTime.now())
            .build();

        final Quiz savedQuiz = quizRepository.save(quiz);

        final List<QuizCard> quizCards = userCards.stream()
            .map(userCard -> toQuizCard(savedQuiz, userCard.getCard()))
            .toList();

        quizCardRepository.saveAll(quizCards);

        return savedQuiz;
    }

    private QuizCard toQuizCard(Quiz quiz, CardV2 card) {
        return QuizCard.builder()
            .quizId(quiz.getId())
            .cardId(card.getId())
            .status(QuizCardStatus.NEW)
            .build();
    }
}
