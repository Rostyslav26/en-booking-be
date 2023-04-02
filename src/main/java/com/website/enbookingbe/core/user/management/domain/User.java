package com.website.enbookingbe.core.user.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "email", "firstName", "lastName", "imageUrl", "activated", "langKey", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"})
public class User extends AbstractAuditingEntity implements Serializable, UserDetails {

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

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return activated;
    }
}