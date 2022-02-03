package com.archive.sukjulyo.account.repository;

import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.auth.dto.OAuthAttributesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(UUID id);

    Optional<Account> getByEmail(String email);

    /**
     * Select Account Randomly
     *
     * @param exceptIds : Select accounts without that included in this list
     * @param pageable : pageable
     * @return Random Account IDs
     */
    @Query(value = RandomSelectQuery)
    List<UUID> findIdsRandomly(
            @Param("exceptIds") Collection<UUID> exceptIds,
            Pageable pageable
    );

    /**
     * Queries
     */

    String RandomSelectQuery =
            " SELECT id" +
            " FROM Account" +
            " WHERE id NOT IN (:exceptIds)" +
            " ORDER BY random()";

}

