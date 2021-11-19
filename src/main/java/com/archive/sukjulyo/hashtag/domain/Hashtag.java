package com.archive.sukjulyo.hashtag.domain;

import com.archive.sukjulyo.news.domain.News;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class Hashtag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false, unique = true)
    private String tag;

    @Column()
    @ColumnDefault("0")
    private Integer priority;

    @Column()
    @NotNull
    @ColumnDefault("false")
    private Boolean isNoise;

    @ManyToMany(mappedBy="hashtags")
    @JsonBackReference
    private List<News> news = new ArrayList<>();

}
