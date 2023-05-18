package com.website.enbookingbe.client.card.entity;

import com.website.enbookingbe.client.card.model.QuizCardStatus;
import com.website.enbookingbe.client.card.model.QuizStatus;
import com.website.enbookingbe.core.user.management.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 20)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private QuizStatus status;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private Set<QuizCard> quizCards = new LinkedHashSet<>();

    public void addCard(Card card) {
        QuizCard quizCard = new QuizCard();
        quizCard.setCard(card);
        quizCard.setQuiz(this);
        quizCard.setStatus(QuizCardStatus.NEW);
        quizCards.add(quizCard);
    }
}