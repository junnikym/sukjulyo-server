package com.archive.sukjulyo.liked.dto;

import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.liked.domain.Liked;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikedCreationDTO {
    private Long accountId;

    private Account account;
    private Boolean isLike;

    public Liked toEntity() {
        return Liked.builder()
                .account(this.account)
                .isLike(this.isLike)
                .build();
    }
}
