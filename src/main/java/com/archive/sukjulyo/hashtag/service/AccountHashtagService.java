package com.archive.sukjulyo.hashtag.service;

import com.archive.sukjulyo.account.repository.AccountRepository;
import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.hashtag.domain.AccountHashtag;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.AccountHashtagCreateDTO;
import com.archive.sukjulyo.hashtag.dto.AccountHashtagDeleleDTO;
import com.archive.sukjulyo.hashtag.dto.AccountHashtagVO;
import com.archive.sukjulyo.hashtag.repository.AccountHashtagRepository;
import com.archive.sukjulyo.hashtag.repository.HashtagRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountHashtagService {

    private final HashtagRepository hashtagRepository;
    private final AccountHashtagRepository accountHashtagRepository;
    private final AccountRepository accountRepository;

    public List<AccountHashtag> selectAccountHashtagInDetail(Long id, Pageable pageable) {
        var account = accountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target account"
                ));

        return accountHashtagRepository
                .findAllByAccount(account, pageable)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target account's hashtags"
                ));
    }

    /**
     * Select account's preferred hashtag by account's pk id
     *
     * @param id : account's pk id
     * @param nHashtag : number of hashtag
     * @return hash tags
     */
    public List<AccountHashtagVO> selectAccountHashtag(Long id, int nHashtag) {
        var account = accountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target account"
                ));

        return accountHashtagRepository
                .findAllByAccountId(id, Pageable.ofSize(nHashtag))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target account's hashtags"
                ));
    }

    public List<AccountHashtagVO> selectAccountHashtagRandomly(
            List<Long> ExceptIds,
            int nAccount,
            int nHashtag
    ) {
        var selected = accountRepository
                .selectRandomly(ExceptIds, nAccount)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any randomly account"
                ));

        return accountHashtagRepository
                .findAllByAccountList(selected, nHashtag)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any randomly account"
                ));
    }

    /**
     * Select account has hashatg
     */
    public Boolean hasAccountHashatg(Long id) {
        return accountHashtagRepository.existsByAccountId(id);
    }

    /**
     * Create hashatags data that the user prefers
     *
     * @param accountId : account's PK id
     * @param dtos : list of DTO for create account tag
     * @return created AccountHashtag list
     */
    public List<AccountHashtag> createAccountHashtag(long accountId, List<AccountHashtagCreateDTO> dtos) {

        var account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find target account"));

        List<AccountHashtag> result = new ArrayList<>();

        for(var it : dtos) {
            Hashtag hashtag = hashtagRepository
                    .findByTag(it.getHashtag())
                    .orElseThrow(() -> new IllegalArgumentException("Can't find target hashtag"));

            result.add(
                accountHashtagRepository.save(
                    AccountHashtag.builder()
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
        AccountHashtag ent = this.findByAccountAndHashtag(dto.getAccountId(), dto.getHashtag())
                .accountHashtagOpt
                .orElseThrow(() -> new IllegalArgumentException("Can't find target account"));

        accountHashtagRepository.delete(ent);
    }


    /**
     * A class with all the entities required to find AccountHashtag
     */
    @AllArgsConstructor
    private class AllRounder {
        public Account account;
        public Hashtag hashtag;
        public Optional<AccountHashtag> accountHashtagOpt;
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
                accountHashtagRepository.findByAccountAndHashtag(account, hashtag)
        );
    }

}
