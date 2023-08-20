package com.website.enbookingbe.management.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.website.enbookingbe.DBTest;
import com.website.enbookingbe.management.domain.Role;
import com.website.enbookingbe.management.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DBTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSave() {
        final User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail("johndoe@example.com");
        newUser.setPassword("password123");
        newUser.setImageUrl("http://example.com/profile.jpg");
        newUser.setActivated(true);
        newUser.setActivationKey("activation123");
        newUser.setResetKey("reset123");

        final User saved = userRepository.save(newUser);

        assertNotNull(saved);
        assertEquals(1, saved.getId());
        assertEquals("John", saved.getFirstName());
        assertEquals("Doe", saved.getLastName());
        assertEquals("johndoe@example.com", saved.getEmail());
        assertEquals("password123", saved.getPassword());
        assertEquals("http://example.com/profile.jpg", saved.getImageUrl());
        assertTrue(saved.getActivated());
        assertEquals("activation123", saved.getActivationKey());
        assertEquals("reset123", saved.getResetKey());
        assertEquals("en", saved.getLangKey());
        assertEquals("SYSTEM", saved.getCreatedBy());
        assertNotNull(saved.getCreatedDate());
        assertNull(saved.getResetDate());
        assertNull(saved.getLastModifiedBy());
        assertNull(saved.getLastModifiedDate());
    }

    @Test
    @DataSet(value = {"datasets/user/users.xml", "datasets/role/roles.xml", "datasets/user-role/user-roles.xml"})
    void testExistsByEmail() {
        final User user = userRepository.findByEmail("johndoe@example.com").orElse(null);

        assertNotNull(user);
        assertThat(user)
            .usingRecursiveComparison()
            .ignoringFields("createdDate", "lastModifiedDate", "resetDate")
            .isEqualTo(getExpectedUser());
    }

    @Test
    @DataSet(value = {"datasets/user/users.xml", "datasets/role/roles.xml", "datasets/user-role/user-roles.xml"})
    void testFindByActivationKey() {
        final User user = userRepository.findByActivationKey("activation123").orElse(null);

        assertNotNull(user);
        assertThat(user)
            .usingRecursiveComparison()
            .ignoringFields("createdDate", "lastModifiedDate", "resetDate")
            .isEqualTo(getExpectedUser());
    }

    private User getExpectedUser() {
        final User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
        user.setImageUrl("http://example.com/profile.jpg");
        user.setActivated(true);
        user.setActivationKey("activation123");
        user.setResetKey("reset123");
        user.setResetDate(null);
        user.setLangKey("en");
        user.setCreatedBy("admin");
        user.setCreatedDate(null);
        user.setLastModifiedBy("admin");
        user.setLastModifiedDate(null);
        user.setRoles(List.of(new Role("READER")));

        return user;
    }
}