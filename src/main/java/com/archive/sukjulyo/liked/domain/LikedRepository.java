package com.archive.sukjulyo.liked.domain;

import com.archive.sukjulyo.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    Optional<Liked> findByClient(Client client);
}

