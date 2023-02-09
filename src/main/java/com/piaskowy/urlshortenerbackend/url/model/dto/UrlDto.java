package com.piaskowy.urlshortenerbackend.url.model.dto;

import com.piaskowy.urlshortenerbackend.user.model.dto.UserDto;

import java.time.Instant;

public record UrlDto(
        Long id,

        String originalUrl,

        String shortUrl,

        Instant creationDate,

        Instant lastVisited,

        boolean isDisabled,

        Instant expireDate,

        UserDto user
) {
}
