package com.piaskowy.urlshortenerbackend.url.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddNewUrlRequest {
    private String url;
    private LocalDateTime expireDate;
}
