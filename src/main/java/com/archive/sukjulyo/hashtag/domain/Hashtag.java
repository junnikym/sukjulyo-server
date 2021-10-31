package com.archive.sukjulyo.hashtag.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@DynamicInsert
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 64, nullable = false)
    private String tag;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer priority;

}
