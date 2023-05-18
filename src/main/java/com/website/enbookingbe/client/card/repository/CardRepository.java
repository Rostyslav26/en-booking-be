package com.website.enbookingbe.client.card.repository;

import com.website.enbookingbe.client.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {

}