package com.website.enbookingbe.management.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime resetDate;
    private String langKey;
    private List<Role> roles;

    public void addRole(Role role) {
        this.roles.add(role);
    }
}