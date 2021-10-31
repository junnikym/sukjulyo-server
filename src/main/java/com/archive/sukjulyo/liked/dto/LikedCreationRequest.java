package com.archive.sukjulyo.liked.dto;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.liked.domain.Liked;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikedCreationRequest {
    private Client client;
    private boolean islike;

    public Liked toEntity() {
        return Liked.builder()
                .client(client)
                .isLike(islike)
                .build();
    }
}
