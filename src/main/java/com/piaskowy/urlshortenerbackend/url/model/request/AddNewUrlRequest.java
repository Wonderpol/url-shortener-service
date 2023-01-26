package com.piaskowy.urlshortenerbackend.url.model.request;

import lombok.Data;

import java.time.Instant;

@Data
public class AddNewUrlRequest {
    private String url;
    private Instant expireDate;
}
