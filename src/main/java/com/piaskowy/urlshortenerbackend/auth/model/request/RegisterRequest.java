package com.piaskowy.urlshortenerbackend.auth.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record RegisterRequest(
        @Email String email,
        @NotBlank(message = "Password can not be blank")
        String password,
        @NotBlank(message = "Name can not be blank")
        String name,
        @NotBlank(message = "Lastname can not be blank")
        String lastName
) {
}
