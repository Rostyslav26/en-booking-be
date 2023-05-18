package com.website.enbookingbe.management.resource;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResource {

    @NotBlank
    @Email
    @Size(max = 256)
    private String email;

    @NotBlank
    @Size(min = UserValidationConstants.PASSWORD_MIN_LENGTH, max = UserValidationConstants.PASSWORD_MAX_LENGTH)
    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;
}
