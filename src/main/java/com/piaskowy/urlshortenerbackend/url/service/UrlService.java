package com.piaskowy.urlshortenerbackend.url.service;

import com.piaskowy.urlshortenerbackend.url.exception.UrlNotFoundException;
import com.piaskowy.urlshortenerbackend.url.model.Url;
import com.piaskowy.urlshortenerbackend.url.model.dto.UrlDto;
import com.piaskowy.urlshortenerbackend.url.model.mapper.UrlModelMapper;
import com.piaskowy.urlshortenerbackend.url.model.request.AddNewUrlRequest;
import com.piaskowy.urlshortenerbackend.url.model.response.ShortUrlResponse;
import com.piaskowy.urlshortenerbackend.url.repository.UrlRepository;
import com.piaskowy.urlshortenerbackend.user.model.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@Service
@Log4j2
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlModelMapper mapper;
    private final UrlConverterService urlConverterService;
    private final UrlValidationService urlValidationService;

    public UrlService(final UrlRepository urlRepository, final UrlModelMapper mapper, final UrlConverterService urlConverterService, final UrlValidationService urlValidationService) {
        this.urlRepository = urlRepository;
        this.mapper = mapper;
        this.urlConverterService = urlConverterService;
        this.urlValidationService = urlValidationService;
    }

    @Transactional
    public ShortUrlResponse addNewUrl(AddNewUrlRequest addNewUrlRequest, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("Building url with details: " + addNewUrlRequest.toString());

        Url url = Url.builder()
                .user(userDetails.user())
                .creationDate(Instant.now())
                .originalUrl(addNewUrlRequest.url())
                .expireDate(addNewUrlRequest.expireDate())
                .build();

        url = urlRepository.save(url);

        log.info("Creating short url");

        url.setShortUrl(urlConverterService.getShortUrl(url.getId()));

        url = urlRepository.save(url);

        return new ShortUrlResponse(url.getShortUrl());
    }

    public UrlDto getOriginalUrl(String shortUrl) {
        log.info("Trying find url by shortUrl: " + shortUrl);

        Long id = urlConverterService.getOriginalUrlId(shortUrl);
        return urlRepository
                .getUrlById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> {
                    log.error("Short url: " + shortUrl + " not found");
                    return new UrlNotFoundException("Url: " + shortUrl + " not found");
                });
    }

    public URI getRedirectUri(String shortUrl) {
        UrlDto url = getOriginalUrl(shortUrl);

        urlValidationService.validateUrl(url);

        return URI.create(url.getOriginalUrl());
    }

    public List<UrlDto> getAllUrls() {
        return urlRepository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<UrlDto> getAllUserUrls(Long userId) {
        return urlRepository
                .getUrlsByUserId(userId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public void updateOriginalLink(String newLink, Long urlId) {
        log.info("Trying to update link: " + urlId + " with url " + newLink);
        urlRepository.updateUrlOriginalUrlWhereUrlId(urlId, newLink);
    }

    public void deleteLinkById(Long linkId) {
        urlRepository.deleteById(linkId);
    }

}
