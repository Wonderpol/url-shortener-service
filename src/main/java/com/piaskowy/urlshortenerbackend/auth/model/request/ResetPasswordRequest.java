package com.piaskowy.urlshortenerbackend.auth.model.request;

import jakarta.validation.constraints.Email;

public record ResetPasswordRequest(@Email String email) {
}
