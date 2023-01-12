package com.piaskowy.urlshortenerbackend.auth.user.repository;

import com.piaskowy.urlshortenerbackend.auth.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User as u SET u.isEnabled = TRUE WHERE u.email = ?1")
    void enableUserAccount(String email);

}
