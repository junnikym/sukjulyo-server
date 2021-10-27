package com.archive.sukjulyo.hashtag.domain;

import com.archive.sukjulyo.client.domain.Client;
import lombok.Data;

@Data
public class MyHashtagCreationRequest {
    private Client client;
    private Hashtag hashtag;
    private int score;
}
