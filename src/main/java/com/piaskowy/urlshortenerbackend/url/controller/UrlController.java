package com.piaskowy.urlshortenerbackend.url.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/url")
public class UrlController {

    @GetMapping
    public String test() {
        return "TEST";
    }
}
