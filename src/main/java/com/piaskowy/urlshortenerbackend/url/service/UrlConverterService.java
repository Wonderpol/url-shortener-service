package com.piaskowy.urlshortenerbackend.url.service;

import com.piaskowy.urlshortenerbackend.config.EnvironmentVariables;
import com.piaskowy.urlshortenerbackend.url.model.Url;
import com.piaskowy.urlshortenerbackend.url.model.dto.UrlDto;
import com.piaskowy.urlshortenerbackend.url.model.mapper.UrlModelMapper;
import com.piaskowy.urlshortenerbackend.url.model.request.AddNewUrlRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UrlConverterService {

    private final UrlService urlService;
    private final UrlConversionComponent urlConversionComponent;
    private final UrlModelMapper mapper;
    private final EnvironmentVariables environmentVariables;

    UrlConverterService(final UrlService urlService, final UrlConversionComponent urlConversionComponent, final UrlModelMapper mapper, final EnvironmentVariables environmentVariables) {
        this.urlService = urlService;
        this.urlConversionComponent = urlConversionComponent;
        this.mapper = mapper;
        this.environmentVariables = environmentVariables;
    }

    public UrlDto convertUrl(AddNewUrlRequest addNewUrlRequest, Authentication authentication) {
        Url url = urlService.addNewUrl(addNewUrlRequest, authentication);
        String shortUrl = urlConversionComponent.encode(url.getId());

        UrlDto urlDto = mapper.toDto(url);
        urlDto.setShortUrl(environmentVariables.getFrontendUrl() + "/" + shortUrl);

        return urlDto;
    }

}
