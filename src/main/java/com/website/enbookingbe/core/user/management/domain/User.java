package com.website.enbookingbe.core.user.management.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractAuditingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1696738177462315867L;

    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String langKey;
    private String imageUrl;
    private String activationKey;
    private String resetKey;
    private LocalDateTime resetDate;
    private Set<Role> roles;
    private Boolean activated;
}