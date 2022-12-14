package com.website.enbookingbe.core.user.management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String imageUrl;
}
