package com.archive.sukjulyo.history.repository;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.history.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    Optional<History> findById(Long id);
    Optional<History> findByClient(Client client);
}

