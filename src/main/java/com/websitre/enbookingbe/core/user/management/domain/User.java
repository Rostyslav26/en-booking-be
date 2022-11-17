package com.websitre.enbookingbe.core.user.management.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usr")
public class User extends AbstractAuditingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1696738177462315867L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String imageUrl;

    @NotNull
    @Accessors(fluent = true)
    private Boolean activated;

    private String langKey;

    private String activationKey;

    private String resetKey;

    @CreationTimestamp
    private Date resetDate;

    @ManyToMany
    @JoinTable(
        name = "usr_authority",
        joinColumns = {@JoinColumn(name = "usr_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")}
    )
    @BatchSize(size = 20)
    @Setter(AccessLevel.PRIVATE)
    private Set<Authority> authorities = new HashSet<>();

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
}