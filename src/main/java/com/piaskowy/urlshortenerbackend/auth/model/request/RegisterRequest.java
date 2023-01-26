package com.piaskowy.urlshortenerbackend.auth.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    @Email
    private String email;
    @NotBlank(message = "Password can not be blank")
    private String password;
    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "Lastname can not be blank")
    private String lastName;
}
