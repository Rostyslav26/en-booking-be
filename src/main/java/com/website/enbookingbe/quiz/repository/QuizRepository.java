package com.website.enbookingbe.quiz.repository;

import com.website.enbookingbe.quiz.entity.Quiz;
import com.website.enbookingbe.quiz.model.QuizStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    @Query("select q from Quiz q join fetch q.quizCards WHERE q.id = ?1 and q.user.id = ?2")
    Optional<Quiz> findByIdAndAndUserId(Integer quizId, Integer userId);

    @Query("select (count(q) > 0) from Quiz q where q.id = ?1 and q.status = ?2 and q.user.id = ?3")
    boolean isQuizHasStatus(Integer quizId, QuizStatus status, Integer userId);

    boolean existsByIdAndUserId(Integer quizId, Integer userId);
}
