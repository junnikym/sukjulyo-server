package com.archive.sukjulyo.liked.domain;

import com.archive.sukjulyo.client.domain.Client;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean islike;

    @CreationTimestamp
    private LocalDateTime create_at;

//    @ManyToOne
//    @JoinColumn(name = "news_id")
//    private News news;


    @Builder
    public Liked(Client client) {
        this.client = client;
    }
}