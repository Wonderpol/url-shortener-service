package com.piaskowy.urlshortenerbackend.url.model.dto;

import com.piaskowy.urlshortenerbackend.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlDto {
    private Long id;

    private String originalUrl;

    private String shortUrl;

    private Instant creationDate;

    private Instant lastVisited;

    private boolean isDisabled;

    private Instant expireDate;
    private UserDto user;
}
