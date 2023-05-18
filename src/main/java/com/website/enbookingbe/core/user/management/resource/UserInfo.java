package com.website.enbookingbe.core.user.management.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfo {
    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;
}
