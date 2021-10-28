package com.archive.sukjulyo.hashtag.domain;
import lombok.Data;

@Data
public class HashtagCreationRequest {
    private String tag;
    private int priority;
}
