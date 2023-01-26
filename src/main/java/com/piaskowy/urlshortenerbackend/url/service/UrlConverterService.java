package com.piaskowy.urlshortenerbackend.url.service;

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

    UrlConverterService(final UrlService urlService, final UrlConversionComponent urlConversionComponent, final UrlModelMapper mapper) {
        this.urlService = urlService;
        this.urlConversionComponent = urlConversionComponent;
        this.mapper = mapper;
    }

    public UrlDto convertUrl(AddNewUrlRequest addNewUrlRequest, Authentication authentication) {
        Url url = urlService.addNewUrl(addNewUrlRequest, authentication);
        String shortUrl = urlConversionComponent.encode(url.getId());

        UrlDto urlDto = mapper.toDto(url);
        urlDto.setShortUrl(shortUrl);

        return urlDto;
    }

}
