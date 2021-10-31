package com.archive.sukjulyo.history.dto;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.history.domain.History;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryCreationRequest {
    private Client client;
    private LocalDateTime create_at;

    public History toEntity() {
        return History.builder()
                .client(client)
                .build();
    }
}
