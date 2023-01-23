package com.piaskowy.urlshortenerbackend.url.model.dto;

import com.piaskowy.urlshortenerbackend.auth.user.model.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlDto {
    private Long id;
    private String url;
    private String shortUrl;
    private LocalDateTime creationDate;
    private LocalDateTime lastVisited;
    private UserDto user;
}
