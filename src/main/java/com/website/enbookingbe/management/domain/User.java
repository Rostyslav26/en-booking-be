package com.website.enbookingbe.management.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class User extends AbstractAuditingEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String imageUrl;
    private Boolean activated;
    private String activationKey;
    private String resetKey;
    private Instant resetDate;
    private String langKey;
    private Set<Role> roles = new LinkedHashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }
}