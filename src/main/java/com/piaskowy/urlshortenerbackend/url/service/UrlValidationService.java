package com.piaskowy.urlshortenerbackend.url.service;

import com.piaskowy.urlshortenerbackend.url.exception.UrlException;
import com.piaskowy.urlshortenerbackend.url.model.dto.UrlDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class UrlValidationService {

    public void validateUrl(UrlDto url) {

        if (url.expireDate() != null) {
            if (Instant.now().isAfter(url.expireDate())) {
                log.error("Url: " + url.shortUrl() + " expired");
                throw new UrlException("Url: " + url.shortUrl() + " expired");
            }
        }

        if (url.isDisabled()) {
            log.error("Url: " + url.shortUrl() + " is disabled by author");
            throw new UrlException("Url: " + url.shortUrl() + " is disabled by author");
        }
    }
}
