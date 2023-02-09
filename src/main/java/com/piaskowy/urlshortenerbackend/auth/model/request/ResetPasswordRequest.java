package com.piaskowy.urlshortenerbackend.auth.model.request;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(@NotBlank String email) {
}
