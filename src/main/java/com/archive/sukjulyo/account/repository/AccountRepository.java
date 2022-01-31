package com.archive.sukjulyo.account.repository;

import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.auth.dto.OAuthAttributesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long id);

    Optional<Account> getByEmail(String email);

    @Query(value = "SELECT id " +
                   "FROM account " +
                   "WHERE id NOT IN (:exeptIds) " +
                   "ORDER BY RAND() LIMIT :n",
            nativeQuery = true)
    Optional<List<String>> selectRandomly(
            @Param("exeptIds") List<Long> exeptIds,
            @Param("n") Integer size
    );

}

