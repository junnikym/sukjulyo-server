package com.archive.sukjulyo.hashtag.repository;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.hashtag.domain.ClientHashtag;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientHashtagRepository extends JpaRepository<ClientHashtag, Long> {

    Optional<List<ClientHashtag>> findAllByClient(Client client);

    Optional<ClientHashtag> findByClientAndHashtag(Client client, Hashtag hashtag);

}

