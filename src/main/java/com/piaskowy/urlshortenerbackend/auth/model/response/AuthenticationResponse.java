package com.piaskowy.urlshortenerbackend.auth.model.response;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
