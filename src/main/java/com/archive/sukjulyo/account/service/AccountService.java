package com.archive.sukjulyo.account.service;

import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.account.repository.AccountRepository;
import com.archive.sukjulyo.auth.dto.OAuthAttributesDTO;
import com.archive.sukjulyo.util.ClassConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    /**
     * Select Account by account's primary key ID
     *
     * @param id : clinet's parimary key
     * @return Account entity
     */
    public Account selectAccount(Long id) {
        return accountRepository
                .findById(id)
                .orElse(null);
    }


    /**
     * When OAuth2 Login, Insert or Update the Account data
     *
     * @param oAuthAttributes : OAuth2 user data
     * @return inserted / updated account data
     */
    @Transactional()
    public Account insertAndUpdateAccount(OAuthAttributesDTO oAuthAttributes) {

        Function<Account, Account> convertWithoutNull = (entity) -> {
            try{
                return ClassConverter.convertWithoutNull(oAuthAttributes, entity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Account account = accountRepository
                .getByEmail(oAuthAttributes.getEmail())
                .map(convertWithoutNull)
                .orElse(oAuthAttributes.toAccount());

        return accountRepository.save(account);
    }

    /**
     * Delete Account
     *
     * @param id : account's PK id
     */
    @Transactional()
    public void deleteAccount(Long id){
        accountRepository.deleteById(id);
    }
}
