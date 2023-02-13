package com.piaskowy.urlshortenerbackend.token.model;

import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_token")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NonNull
    private String generatedToken;
    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    private LocalDateTime expiresAt;
    private LocalDateTime usedAt;
    @NonNull
    private TokenType tokenType;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
