package com.archive.sukjulyo.hashtag.dto;

import com.archive.sukjulyo.account.domain.Account;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class AccountHashtagCreateDTO {

    @NotNull
    private String hashtag;

    @NotNull
    private Integer score;
    
}
