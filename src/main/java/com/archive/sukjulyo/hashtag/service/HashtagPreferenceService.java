package com.archive.sukjulyo.hashtag.service;

import com.archive.sukjulyo.account.repository.AccountRepository;
import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.hashtag.domain.HashtagPreference;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.AccountHashtagCreateDTO;
import com.archive.sukjulyo.hashtag.dto.AccountHashtagDeleleDTO;
import com.archive.sukjulyo.hashtag.repository.HashtagPreferenceRepository;
import com.archive.sukjulyo.hashtag.repository.HashtagRepository;
import com.archive.sukjulyo.hashtag.vo.HashtagPreferenceVO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HashtagPreferenceService {

    private final HashtagRepository hashtagRepository;
    private final HashtagPreferenceRepository hashtagPreferenceRepository;
    private final AccountRepository accountRepository;


    /**
     * Select all data of HashtagPreference that the user has.
     *
     * @param id : User Account ID
     * @param pageable : pageable
     * @return Hashtag Preference Data
     */
    public List<HashtagPreference> selectAccountHashtagInDetail(UUID id, Pageable pageable) {
        var account = accountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target account"
                ));

        var result = hashtagPreferenceRepository
                .findAllByAccount(account, pageable);

        if(result.isEmpty())
            throw new IllegalArgumentException("Can't find any target account's hashtags");

        return result;
    }

    /**
     * Select account's preferred hashtag Top N by account's pk id
     *
     * @param id : account's pk id
     * @param nHashtag : number of hashtag
     * @return hash tags
     */
    public List<HashtagPreferenceVO> selectAccountHashtag(UUID id, int nHashtag) {
        var account = accountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target account"
                ));

        var result = hashtagPreferenceRepository
                .findAllByAccountOrderByScoreDesc(account, Pageable.ofSize(nHashtag));

        if(result.isEmpty())
            throw new IllegalArgumentException("Can't find any target account's hashtags");

        return result;
    }

//    public List<HashtagPreferenceVO> selectAccountHashtagRandomly(
//            List<UUID> ExceptIds,
//            int nAccount,
//            int nHashtag
//    ) {
//        var selected = accountRepository
//                .findIdsRandomly(ExceptIds, PageRequest.of(0, nAccount));
//
//        if(selected.isEmpty())
//            throw new IllegalArgumentException("Can't find any randomly account");
//
//        return hashtagPreferenceRepository.findAllByAccountList(selected);
//    }

    /**
     *
     *
     * @param id
     * @return
     */
    public Boolean hasAccountHashtag(Long id) {
        return hashtagPreferenceRepository.existsByAccountId(id);
    }

    /**
     * Create hashatags data that the user prefers
     *
     * @param accountId : account's PK id
     * @param dtos : list of DTO for create account tag
     * @return created AccountHashtag list
     */
    public List<HashtagPreference> createAccountHashtag(long accountId, List<AccountHashtagCreateDTO> dtos) {

        var account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find target account"));

        List<HashtagPreference> result = new ArrayList<>();

        for(var it : dtos) {
            Hashtag hashtag = hashtagRepository
                    .findByTag(it.getHashtag())
                    .orElseThrow(() -> new IllegalArgumentException("Can't find target hashtag"));

            result.add(
                hashtagPreferenceRepository.save(
                    HashtagPreference.builder()
                        .account(account)
                        .hashtag(hashtag)
                        .score(it.getScore())
                        .build()
                )
            );
        }

        return result;
    }


    /**
     * Delete the account preferred hashtag
     *
     * @param dto : DTO for delele AccountHashtag
     */
    public void deleteAccountHashtag(AccountHashtagDeleleDTO dto) {
        HashtagPreference ent = this.findByAccountAndHashtag(dto.getAccountId(), dto.getHashtag())
                .accountHashtagOpt
                .orElseThrow(() -> new IllegalArgumentException("Can't find target account"));

        hashtagPreferenceRepository.delete(ent);
    }


    /**
     * A class with all the entities required to find AccountHashtag
     */
    @AllArgsConstructor
    private class AllRounder {
        public Account account;
        public Hashtag hashtag;
        public Optional<HashtagPreference> accountHashtagOpt;
    }


    /**
     * Find out if the account has a hashtag.
     *
     * @param accountId : account's PK id
     * @param tag : hashtag
     * @return AllRounder class with found entities
     */
    private AllRounder findByAccountAndHashtag(Long accountId, String tag) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find target account"));

        Hashtag hashtag = hashtagRepository
                .findByTag(tag)
                .orElseThrow(() -> new IllegalArgumentException("Can't find target hashtag"));

        return new AllRounder (
                account,
                hashtag,
                hashtagPreferenceRepository.findByAccountAndHashtag(account, hashtag)
        );
    }

}
