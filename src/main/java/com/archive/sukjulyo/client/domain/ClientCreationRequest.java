package com.archive.sukjulyo.client.domain;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.Data;

import java.util.List;

@Data
public class ClientCreationRequest {
    private String refreshtoken;
    private String agerange;
    private String gender;
    private List<Hashtag> hashtags;
}
