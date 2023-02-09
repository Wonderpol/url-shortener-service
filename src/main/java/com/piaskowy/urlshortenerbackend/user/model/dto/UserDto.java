package com.piaskowy.urlshortenerbackend.user.model.dto;

public record UserDto(
        Long id,
        String email,
        String name,
        String lastName
) {
}
