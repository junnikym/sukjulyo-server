package com.archive.sukjulyo.hashtag.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 64, nullable = false)
    private String tag;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int priority;


    @Builder
    public Hashtag(String tag, int priority) {
        this.tag = tag;
        this.priority = priority;
    }
}
