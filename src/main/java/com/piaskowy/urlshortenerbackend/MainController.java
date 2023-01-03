package com.piaskowy.urlshortenerbackend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/main")
public class MainController {

    @GetMapping
    public String getHello() {
        return "Hello";
    }

    @GetMapping("/securedHello")
    public String securedHello() {
        return "Secured Hello";
    }

}
