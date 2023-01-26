package com.piaskowy.urlshortenerbackend.url.model.dto;

import com.piaskowy.urlshortenerbackend.user.model.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlDto {
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime creationDate;
    private LocalDateTime lastVisited;
    private LocalDateTime expireDate;
    private UserDto user;
}
