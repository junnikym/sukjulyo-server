package com.archive.sukjulyo.liked.dto;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.liked.domain.Liked;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikedCreationDTO {
    private Long clientId;

    private Client client;
    private Boolean isLike;

    public Liked toEntity() {
        return Liked.builder()
                .client(this.client)
                .isLike(this.isLike)
                .build();
    }
}
