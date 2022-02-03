package com.archive.sukjulyo.hashtag.repository;

import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.hashtag.domain.HashtagPreference;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.vo.HashtagPreferenceVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HashtagPreferenceRepository extends JpaRepository<HashtagPreference, Long> {


    /**
     * ( SELECT ) HashtagPreference
     * WHERE    Account
     *
     * @param account : target account
     * @param pageable : pageable
     * @return Selected HashtagPreference
     */
    List<HashtagPreference> findAllByAccount(Account account, Pageable pageable);

    /**
     * ( SELECT ) HashtagPreference
     * WHERE        Account
     * ORDER BY     Score (DESC)
     *
     * @param account : target account
     * @param pageable : pageable
     * @return Selected HashtagPreference
     */
    List<HashtagPreferenceVO> findAllByAccountOrderByScoreDesc(Account account, Pageable pageable);

//    @Query(value = rankHashtagSubQuery)
//    List<HashtagPreferenceVO> findAllByAccountList (
//            @Param("accounts") List<UUID> accounts
//            @Param("n") int n
//    );

    Optional<HashtagPreference> findByAccountAndHashtag(Account account, Hashtag hashtag);

    Boolean existsByAccountId(Long id);

}

