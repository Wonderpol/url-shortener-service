package com.piaskowy.urlshortenerbackend.url.model;

import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Nonnull
    private String originalUrl;
    @Nonnull
    private String shortUrl;
    @Nonnull
    private Instant creationDate;
    private Instant lastVisited;
    private Instant expireDate;
    private boolean isDisabled;
    @ManyToOne
    private User user;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final Url url = (Url) o;
        return id != null && Objects.equals(id, url.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
