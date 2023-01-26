package com.piaskowy.urlshortenerbackend.url.service;

import com.piaskowy.urlshortenerbackend.url.exception.UrlNotFoundException;
import com.piaskowy.urlshortenerbackend.url.model.Url;
import com.piaskowy.urlshortenerbackend.url.model.dto.UrlDto;
import com.piaskowy.urlshortenerbackend.url.model.mapper.UrlModelMapper;
import com.piaskowy.urlshortenerbackend.url.model.request.AddNewUrlRequest;
import com.piaskowy.urlshortenerbackend.url.repository.UrlRepository;
import com.piaskowy.urlshortenerbackend.user.model.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Log4j2
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlModelMapper mapper;
    private final UrlConverterService urlConverterService;

    public UrlService(final UrlRepository urlRepository, final UrlModelMapper mapper, final UrlConverterService urlConverterService) {
        this.urlRepository = urlRepository;
        this.mapper = mapper;
        this.urlConverterService = urlConverterService;
    }

    @Transactional
    public UrlDto addNewUrl(AddNewUrlRequest addNewUrlRequest, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("Building url with details: " + addNewUrlRequest.toString());

        Url url = Url.builder()
                .user(userDetails.user())
                .creationDate(Instant.now())
                .originalUrl(addNewUrlRequest.getUrl())
                .expireDate(addNewUrlRequest.getExpireDate())
                .build();

        url = urlRepository.save(url);

        log.info("Creating short url");

        url.setShortUrl(urlConverterService.convertUrl(url.getId()));

        return mapper.toDto(urlRepository.save(url));
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

}
