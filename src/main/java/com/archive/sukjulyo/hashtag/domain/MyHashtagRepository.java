package com.archive.sukjulyo.hashtag.domain;

import com.archive.sukjulyo.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyHashtagRepository extends JpaRepository<MyHashtag, Long> {
    Optional<MyHashtag> findByClient(Client client);
}

