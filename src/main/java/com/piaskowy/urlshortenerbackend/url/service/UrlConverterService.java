package com.piaskowy.urlshortenerbackend.url.service;

import com.piaskowy.urlshortenerbackend.config.EnvironmentVariables;
import org.springframework.stereotype.Service;

import static com.piaskowy.urlshortenerbackend.url.service.utils.UrlConversionUtils.decode;
import static com.piaskowy.urlshortenerbackend.url.service.utils.UrlConversionUtils.encode;

@Service
public class UrlConverterService {

    private final EnvironmentVariables environmentVariables;

    UrlConverterService(final EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public String getShortUrl(Long id) {
        String shortUrl = encode(id);

        return environmentVariables.getFrontendUrl() + "/" + shortUrl;
    }

    public Long getOriginalUrlId(String shortUrl) {
        return decode(shortUrl);
    }

}
