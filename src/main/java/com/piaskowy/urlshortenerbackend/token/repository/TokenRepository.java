package com.piaskowy.urlshortenerbackend.token.repository;

import com.piaskowy.urlshortenerbackend.token.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findTokenByGeneratedToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Token as t SET t.usedAt = ?2 where t.generatedToken = ?1")
    void setTokenConfirmationDate(String token, LocalDateTime localDateTime);
}
