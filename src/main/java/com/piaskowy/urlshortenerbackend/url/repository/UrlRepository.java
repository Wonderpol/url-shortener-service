package com.piaskowy.urlshortenerbackend.url.repository;

import com.piaskowy.urlshortenerbackend.url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> getUrlByShortUrl(String shortUrl);

    Optional<Url> getUrlById(Long id);

    List<Url> getUrlsByUserId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Url as u SET u.originalUrl = ?2 WHERE u.id = ?1")
    void updateUrlOriginalUrlWhereUrlId(Long id, String originalUrl);
}
