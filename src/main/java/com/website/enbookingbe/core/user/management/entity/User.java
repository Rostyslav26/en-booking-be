package com.website.enbookingbe.core.user.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.website.enbookingbe.client.card.entity.Card;
import com.website.enbookingbe.client.card.entity.Quiz;
import com.website.enbookingbe.client.card.entity.UserCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Size(max = 50)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Size(max = 256)
    @NotNull
    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Size(max = 256)
    @NotNull
    @Column(name = "password", nullable = false, length = 256)
    @JsonIgnore
    private String password;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "activation_key")
    @JsonIgnore
    private String activationKey;

    @Column(name = "reset_key")
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    @JsonIgnore
    private Instant resetDate;

    @Size(max = 5)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Size(max = 50)
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @NotNull
    @Column(name = "last_modified_date", nullable = false)
    private Instant lastModifiedDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnore
    private Set<Quiz> quizzes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnore
    private Set<UserCard> cardCollections = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private Set<Role> roles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnore
    private Set<Card> cards = new LinkedHashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public void addQuiz(Quiz quiz) {
        this.quizzes.add(quiz);
        quiz.setUser(this);
    }

    public void removeQuiz(Quiz quiz) {
        this.quizzes.remove(quiz);
        quiz.setUser(null);
    }

    public void addCard(Card card) {
        this.cards.add(card);
        card.setAuthor(this);

        addToCardCollection(card);
    }

    public void addToCardCollection(Card card) {
        this.cardCollections.add(new UserCard(card, this));
    }
}