package com.piaskowy.urlshortenerbackend.url.service;

import com.piaskowy.urlshortenerbackend.url.UrlRepository;
import com.piaskowy.urlshortenerbackend.url.exception.UrlNotFoundException;
import com.piaskowy.urlshortenerbackend.url.model.Url;
import com.piaskowy.urlshortenerbackend.url.model.dto.UrlDto;
import com.piaskowy.urlshortenerbackend.url.model.mapper.UrlModelMapper;
import com.piaskowy.urlshortenerbackend.url.model.request.AddNewUrlRequest;
import com.piaskowy.urlshortenerbackend.user.model.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlModelMapper mapper;

    public UrlService(final UrlRepository urlRepository, final UrlModelMapper mapper) {
        this.urlRepository = urlRepository;
        this.mapper = mapper;
    }

    public void addNewUrl(AddNewUrlRequest addNewUrlRequest, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Url url = Url.builder()
                .user(userDetails.user())
                .creationDate(LocalDateTime.now())
                .url(addNewUrlRequest.getUrl())
                .shortUrl("test")
                .build();

        urlRepository.save(url);
    }

    public UrlDto getUrlByShortUrl(String shortUrl) {
        log.info("Trying find url by shortUrl: " + shortUrl);
        return urlRepository
                .getUrlByShortUrl(shortUrl)
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

}
