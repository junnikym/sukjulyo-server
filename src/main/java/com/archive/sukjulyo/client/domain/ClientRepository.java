package com.archive.sukjulyo.client.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>
{
    Optional<Client> findById(Long id);
    Optional<Client> findByRefreshtoken(String refreshtoken);
}
