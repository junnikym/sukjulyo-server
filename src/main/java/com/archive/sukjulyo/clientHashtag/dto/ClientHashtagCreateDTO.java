package com.archive.sukjulyo.clientHashtag.dto;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.clientHashtag.domain.ClientHashtag;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.Data;

@Data
public class ClientHashtagCreateDTO {
    private Long clientId;
    private Long hashtagId;

    private Client client;
    private Hashtag hashtag;
    private Integer score;

    public ClientHashtag toEntity() {
        return ClientHashtag.builder()
                .client(this.client)
                .hashtag(this.hashtag)
                .score(this.score)
                .build();
    }
}
