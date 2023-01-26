package com.piaskowy.urlshortenerbackend.auth.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @Email
    private String email;
    @NotBlank(message = "Password can not be empty")
    private String password;
}
