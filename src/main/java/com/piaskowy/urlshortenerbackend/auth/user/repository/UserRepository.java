package com.piaskowy.urlshortenerbackend.auth.user.repository;

import com.piaskowy.urlshortenerbackend.auth.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
