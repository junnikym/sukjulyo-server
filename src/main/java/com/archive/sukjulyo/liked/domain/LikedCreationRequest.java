package com.archive.sukjulyo.liked.domain;

import com.archive.sukjulyo.client.domain.Client;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikedCreationRequest {
    private Client client;
    private boolean islike;
    private LocalDateTime create_at;
}
