package com.website.enbookingbe.quiz.repository;

import com.website.enbookingbe.quiz.entity.QuizCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuizCardRepository extends JpaRepository<QuizCard, Integer> {

    @Query("select qc from QuizCard qc join fetch qc.quiz q join fetch qc.card c where q.id = ?1 and c.id = ?2")
    Optional<QuizCard> findByQuizIdAndCardId(Integer quizId, Integer cardId);
}
