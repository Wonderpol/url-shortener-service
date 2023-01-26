package com.piaskowy.urlshortenerbackend.url.model.dto;

import com.piaskowy.urlshortenerbackend.user.model.dto.UserDto;
import lombok.Data;

import java.time.Instant;

@Data
public class UrlDto {
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private Instant creationDate;
    private Instant lastVisited;
    private Instant expireDate;
    private UserDto user;
}
