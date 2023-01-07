package com.website.enbookingbe.core.user.management.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    @NotBlank
    @Email
    @Size(min = 5, max = 254)
    private String email;

    @NotBlank
    @Size(min = UserValidationConstants.PASSWORD_MIN_LENGTH, max = UserValidationConstants.PASSWORD_MAX_LENGTH)
    private String password;

    @NotBlank
    @Size(min = UserValidationConstants.PASSWORD_MIN_LENGTH, max = UserValidationConstants.PASSWORD_MAX_LENGTH)
    private String confirmedPassword;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 256)
    private String imageUrl;

    @Size(min = 2, max = 10)
    private String langKey;
}
