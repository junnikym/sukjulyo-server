package com.archive.sukjulyo.clientHashtag.repository;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.clientHashtag.domain.ClientHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientHashtagRepository extends JpaRepository<ClientHashtag, Long> {
    Optional<ClientHashtag> findByClient(Client client);
}

