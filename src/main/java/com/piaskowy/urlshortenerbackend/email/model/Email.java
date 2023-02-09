package com.piaskowy.urlshortenerbackend.email.model;

import lombok.Builder;
import lombok.NonNull;

import java.util.Map;

@Builder
public record Email(
        @NonNull
        String to,
        @NonNull
        String from,
        @NonNull
        String subject,
        String text,
        @NonNull
        String template,
        Map<String, Object> properties
) {
}
