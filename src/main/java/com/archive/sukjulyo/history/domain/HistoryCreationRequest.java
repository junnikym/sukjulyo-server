package com.archive.sukjulyo.history.domain;

import com.archive.sukjulyo.client.domain.Client;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryCreationRequest {
    private Client client;
    private LocalDateTime create_at;
}
