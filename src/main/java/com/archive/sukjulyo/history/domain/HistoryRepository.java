package com.archive.sukjulyo.history.domain;

import com.archive.sukjulyo.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    Optional<History> findByClient(Client client);
}

