package com.piaskowy.urlshortenerbackend.url.controller;

import com.piaskowy.urlshortenerbackend.url.model.dto.UrlDto;
import com.piaskowy.urlshortenerbackend.url.model.request.AddNewUrlRequest;
import com.piaskowy.urlshortenerbackend.url.model.response.ShortUrlResponse;
import com.piaskowy.urlshortenerbackend.url.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(final UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ShortUrlResponse convertUrl(@Valid @RequestBody AddNewUrlRequest addNewUrlRequest, Authentication authentication) {
        return urlService.addNewUrl(addNewUrlRequest, authentication);
    }

    @GetMapping(params = "shortUrl")
    public UrlDto getOriginalUrl(@RequestParam String shortUrl) {
        return urlService.getOriginalUrl(shortUrl);
    }

    @GetMapping
    public List<UrlDto> getAllUrls() {
        return urlService.getAllUrls();
    }

    @GetMapping(params = "userId")
    public List<UrlDto> getAllUserUrls(@RequestParam Long userId) {
        return urlService.getAllUserUrls(userId);
    }

    @GetMapping(value = "/redirect/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        URI redirectUri = urlService.getRedirectUri(shortUrl);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
    }
}
