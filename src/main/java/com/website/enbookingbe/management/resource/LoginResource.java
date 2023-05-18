package com.website.enbookingbe.management.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginResource {

    @NotNull
    private String email;

    @NotNull
    private String password;

    private boolean rememberMe;
}
