package com.piaskowy.urlshortenerbackend.url;

import com.piaskowy.urlshortenerbackend.url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> getUrlByShortUrl(String shortUrl);
}