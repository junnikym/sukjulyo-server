package com.archive.sukjulyo.client.domain;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 128, nullable = false)
    private String refreshtoken;

    @Column(length = 8, nullable = true)
    private String agerange;

    @Column(length = 8, nullable = true)
    private String gender;

    @OneToMany
    @JoinColumn(name = "hashtag_id")
    private List<Hashtag> hashtags = new ArrayList<>();

    @Builder
    public Client(String refreshtoken, String agerange, String gender) {
        this.refreshtoken = refreshtoken;
        this.agerange = agerange;
        this.gender = gender;
    }
}

