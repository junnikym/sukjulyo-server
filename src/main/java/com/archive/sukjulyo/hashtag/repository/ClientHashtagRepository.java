package com.archive.sukjulyo.hashtag.repository;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.hashtag.domain.ClientHashtag;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.ClientHashtagVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientHashtagRepository extends JpaRepository<ClientHashtag, Long> {


    Optional<List<ClientHashtag>> findAllByClient(Client clientId, Pageable pageable);

    @Query(value = "SELECT id, score, client_id AS clientId, hashtag_id AS hashtagID " +
                   "FROM client_hashtag " +
                   "WHERE client_id = :id ORDER BY score DESC ",
            nativeQuery = true)
    Optional<List<ClientHashtagVO>> findAllByClientId(@Param("id") long clientId, Pageable pageable);

    @Query(value = "SELECT " +
                   "    ch_rank.id, ch_rank.score, " +
                   "    ch_rank.client_id AS clientId, " +
                   "    ch_rank.hashtag_id AS HashtagId " +
                   "FROM ( " +
                   "    SELECT " +
                   "        ch.id, ch.score, ch.client_id, ch.hashtag_id, " +
                   "        ROW_NUMBER() OVER(PARTITION BY ch.client_id ORDER BY ch.score DESC) row_num " +
                   "    FROM client_hashtag AS ch " +
                   "    WHERE ch.client_id IN (:clientList)" +
                   "    ORDER BY ch.client_id " +
                   ") ch_rank " +
                   "WHERE ch_rank.row_num <= :n",
            nativeQuery = true)
    Optional<List<ClientHashtagVO>> findAllByClientList(
            @Param("clientList") List<String> client,
            @Param("n")          int          size
    );

    Optional<ClientHashtag> findByClientAndHashtag(Client client, Hashtag hashtag);

    Boolean existsByClientId(Long id);

}

