package com.piaskowy.urlshortenerbackend.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String path;

}
