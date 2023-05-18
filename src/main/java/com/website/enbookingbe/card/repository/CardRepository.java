package com.website.enbookingbe.card.repository;

import com.website.enbookingbe.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {

}