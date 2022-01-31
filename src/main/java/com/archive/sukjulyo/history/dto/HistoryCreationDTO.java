package com.archive.sukjulyo.history.dto;

import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.history.domain.History;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryCreationDTO {
    private Long accountId;

    private Account account;
    private LocalDateTime create_at;

    public History toEntity() {
        return History.builder()
                .account(account)
                .build();
    }
}
