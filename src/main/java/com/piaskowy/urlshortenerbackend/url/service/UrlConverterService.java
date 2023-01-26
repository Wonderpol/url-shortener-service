package com.piaskowy.urlshortenerbackend.url.service;

import com.piaskowy.urlshortenerbackend.config.EnvironmentVariables;
import org.springframework.stereotype.Service;

@Service
public class UrlConverterService {

    private final UrlConversionComponent urlConversionComponent;
    private final EnvironmentVariables environmentVariables;

    UrlConverterService(final UrlConversionComponent urlConversionComponent, final EnvironmentVariables environmentVariables) {
        this.urlConversionComponent = urlConversionComponent;
        this.environmentVariables = environmentVariables;
    }

    public String convertUrl(Long id) {
        String shortUrl = urlConversionComponent.encode(id);

        return environmentVariables.getFrontendUrl() + "/" + shortUrl;
    }

    public Long getOriginalUrlId(String shortUrl) {
        return urlConversionComponent.decode(shortUrl);
    }

}
