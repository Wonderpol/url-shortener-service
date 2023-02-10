package com.piaskowy.urlshortenerbackend.url.model.request;

import java.time.Instant;

public record AddNewUrlRequest(String url, Instant expireDate) {
}
