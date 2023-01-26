package com.piaskowy.urlshortenerbackend.url.repository;

import com.piaskowy.urlshortenerbackend.url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> getUrlByShortUrl(String shortUrl);

    Optional<Url> getUrlById(Long id);

    List<Url> getUrlsByUserId(Long id);
}
