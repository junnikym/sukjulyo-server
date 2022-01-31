package com.archive.sukjulyo.liked.repository;

import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.liked.domain.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    Optional<Liked> findByAccount(Account account);
}

