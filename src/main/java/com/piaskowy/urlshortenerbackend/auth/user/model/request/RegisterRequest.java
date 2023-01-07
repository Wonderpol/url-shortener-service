package com.piaskowy.urlshortenerbackend.auth.user.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String lastName;
}
