package com.archive.sukjulyo.hashtag.domain;

import com.archive.sukjulyo.client.domain.Client;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
public class MyHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @Column(nullable = false)
    private int score;

    @Builder
    public MyHashtag(Client client, Hashtag hashtag, int score) {
        this.client = client;
        this.hashtag = hashtag;
        this.score = score;
    }
}