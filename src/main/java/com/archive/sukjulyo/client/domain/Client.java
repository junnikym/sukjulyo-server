package com.archive.sukjulyo.client.domain;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 128, nullable = false)
    private String refresh_token;

    @Column(length = 8, nullable = true)
    private String age_range;

    @Column(length = 8, nullable = true)
    private String gender;

    @OneToMany
    @JoinColumn(name = "hashtag_id")
    private List<Hashtag> hashtags = new ArrayList<>();

    @Builder
    public Client(String refresh_token, String age_range, String gender) {
        this.refresh_token = refresh_token;
        this.age_range = age_range;
        this.gender = gender;
    }
}

