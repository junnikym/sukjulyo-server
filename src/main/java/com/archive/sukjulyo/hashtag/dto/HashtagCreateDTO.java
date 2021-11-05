package com.archive.sukjulyo.hashtag.dto;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HashtagCreateDTO {
    private String tag;
    private Integer priority = 0;

    public Hashtag toEntity() {
        return Hashtag.builder()
                .tag(this.tag)
                .priority(this.priority)
                .build();
    }
}
