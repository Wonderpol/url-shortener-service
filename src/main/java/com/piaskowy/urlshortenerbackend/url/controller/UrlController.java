package com.piaskowy.urlshortenerbackend.url.controller;

import com.piaskowy.urlshortenerbackend.url.model.dto.UrlDto;
import com.piaskowy.urlshortenerbackend.url.model.request.AddNewUrlRequest;
import com.piaskowy.urlshortenerbackend.url.service.UrlService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(final UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public UrlDto convertUrl(@RequestBody AddNewUrlRequest addNewUrlRequest, Authentication authentication) {
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
}
