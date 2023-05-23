package com.website.enbookingbe.quiz;

import com.website.enbookingbe.card.Card;
import com.website.enbookingbe.card.CardService;
import com.website.enbookingbe.exception.NotFoundException;
import com.website.enbookingbe.management.UserService;
import com.website.enbookingbe.management.entity.User;
import com.website.enbookingbe.quiz.entity.Quiz;
import com.website.enbookingbe.quiz.model.QuizStatus;
import com.website.enbookingbe.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {
    private final CardService cardService;
    private final UserService userService;
    private final QuizRepository quizRepository;

    @Transactional(readOnly = true)
    public Optional<Quiz> getQuizById(Integer quizId, Integer userId) {
        return quizRepository.findByIdAndAndUserId(quizId, userId);
    }

    public Quiz createQuiz(List<Integer> cardIds, Integer userId) {
        final List<Card> cards = cardService.getUserCards(userId, cardIds);

        return createQuiz(userId, cards);
    }

    public Quiz createQuizByNotLearnedCards(Integer userId, @Nullable Integer limit) {
        final List<Card> cards = cardService.getNotLearnedByUserId(userId, limit);

        return createQuiz(userId, cards);
    }

    private Quiz createQuiz(Integer userId, List<Card> cards) {
        if (cards.isEmpty()) {
            throw new NotFoundException("No cards found for user with id: " + userId);
        }

        final User user = userService.getById(userId);

        final Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setStatus(QuizStatus.CREATED);
        quiz.setCreatedAt(LocalDateTime.now());

        cards.forEach(quiz::addCard);

        return quizRepository.save(quiz);
    }
}
