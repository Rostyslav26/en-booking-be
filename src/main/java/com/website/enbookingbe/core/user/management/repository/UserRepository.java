package com.website.enbookingbe.core.user.management.repository;

import com.website.enbookingbe.core.user.management.entity.User;
import com.website.enbookingbe.core.user.management.resource.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByActivationKey(String activationKey);

    @Query(value = "select  "
                   + "u.id as userId, "
                   + "u.email, "
                   + "u.first_name as firstName, "
                   + "u.last_name as lastName, "
                   + "u.image_url as imageUrl "
                   + "from \"user\" u "
                   + "where u.id = ?1", nativeQuery = true)
    Optional<UserInfo> getUserInfoById(Integer id);

}