package com.website.enbookingbe.core.user.management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfile {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
}
