package com.website.enbookingbe.card.repository;

import com.website.enbookingbe.quiz.entity.UserCard;
import com.website.enbookingbe.quiz.entity.UserCardId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard, UserCardId> {

    @Query("select u from UserCard u where u.learned = false and u.user.id = ?1")
    List<UserCard> findNotLearnedByUserId(Integer userId, Pageable pageable);

    List<UserCard> findByUserId(Integer userId);

    List<UserCard> findByUserIdAndCardIdIn(Integer userId, List<Integer> cardIds);
}