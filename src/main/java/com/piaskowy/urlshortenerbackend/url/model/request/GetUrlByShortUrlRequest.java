package com.piaskowy.urlshortenerbackend.url.model.request;

import lombok.Data;

@Data
public class GetUrlByShortUrlRequest {
    private String shortUrl;
}
