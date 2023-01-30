package com.piaskowy.urlshortenerbackend.url.model.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class ShortUrlResponse {
    @NonNull
    private String shortUrl;
}
