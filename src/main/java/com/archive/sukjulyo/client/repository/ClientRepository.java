package com.archive.sukjulyo.client.repository;

import com.archive.sukjulyo.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findById(Long id);

    @Query(value = "SELECT id " +
                   "FROM client " +
                   "WHERE id NOT IN (:exeptIds) " +
                   "ORDER BY RAND() LIMIT :n",
            nativeQuery = true)
    Optional<List<String>> selectRandomly(
            @Param("exeptIds") List<Long> exeptIds,
            @Param("n") Integer size
    );

}

