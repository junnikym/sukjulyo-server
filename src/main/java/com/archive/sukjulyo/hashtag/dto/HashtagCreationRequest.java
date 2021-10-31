package com.archive.sukjulyo.hashtag.dto;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.Data;

@Data
public class HashtagCreationRequest {
    private String tag;
    private int priority;

    public Hashtag toEntity() {
        return Hashtag.builder()
                .tag(this.tag)
                .priority(priority)
                .build();
    }
}
