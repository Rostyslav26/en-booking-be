package com.websitre.enbookingbe.core.user.management.repository;

import com.websitre.enbookingbe.core.user.management.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select distinct u from User u left join fetch u.authorities where lower(u.email) = lower(:email)")
    Optional<User> findOneWithAuthoritiesByEmail(String email);

    @Query("select (count(u) > 0) from User u where u.email = ?1")
    boolean existsByEmail(String email);

    Optional<User> findByActivationKey(String key);
}