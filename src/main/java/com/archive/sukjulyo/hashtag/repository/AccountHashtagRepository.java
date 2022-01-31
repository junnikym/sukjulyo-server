package com.archive.sukjulyo.hashtag.repository;

import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.hashtag.domain.AccountHashtag;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.AccountHashtagVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountHashtagRepository extends JpaRepository<AccountHashtag, Long> {


    Optional<List<AccountHashtag>> findAllByAccount(Account accountId, Pageable pageable);

    @Query(value = "SELECT id, score, account_id AS accountId, hashtag_id AS hashtagID " +
                   "FROM account_hashtag " +
                   "WHERE account_id = :id ORDER BY score DESC ",
            nativeQuery = true)
    Optional<List<AccountHashtagVO>> findAllByAccountId(@Param("id") long accountId, Pageable pageable);

    @Query(value = "SELECT " +
                   "    ch_rank.id, ch_rank.score, " +
                   "    ch_rank.account_id AS accountId, " +
                   "    ch_rank.hashtag_id AS HashtagId " +
                   "FROM ( " +
                   "    SELECT " +
                   "        ch.id, ch.score, ch.account_id, ch.hashtag_id, " +
                   "        ROW_NUMBER() OVER(PARTITION BY ch.account_id ORDER BY ch.score DESC) row_num " +
                   "    FROM account_hashtag AS ch " +
                   "    WHERE ch.account_id IN (:accountList)" +
                   "    ORDER BY ch.account_id " +
                   ") ch_rank " +
                   "WHERE ch_rank.row_num <= :n",
            nativeQuery = true)
    Optional<List<AccountHashtagVO>> findAllByAccountList(
            @Param("accountList") List<String> account,
            @Param("n")          int          size
    );

    Optional<AccountHashtag> findByAccountAndHashtag(Account account, Hashtag hashtag);

    Boolean existsByAccountId(Long id);

}

