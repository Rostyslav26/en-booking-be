package com.website.enbookingbe.core.user.management.repository;

import com.website.enbookingbe.core.user.management.entity.User;
import com.website.enbookingbe.core.user.management.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByActivationKey(String activationKey);

    @Query("select new com.website.enbookingbe.core.user.management.model.UserInfo(u.id, u.email, u.firstName, u.lastName, u.imageUrl) from User u where u.id = ?1")
    Optional<UserInfo> getUserInfoById(Integer id);
}