package com.archive.sukjulyo.hashtag.dto;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.hashtag.domain.ClientHashtag;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class ClientHashtagCreateDTO {

    @NotNull
    private Long clientId;

    @NotNull
    private String Hashtag;

    @NotNull
    private Integer score;
    
}
