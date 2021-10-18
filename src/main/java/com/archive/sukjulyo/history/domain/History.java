package com.archive.sukjulyo.history.domain;

import com.archive.sukjulyo.client.domain.Client;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @CreationTimestamp
    private LocalDateTime create_at;

//    @ManyToOne
//    @JoinColumn(name = "news_id")
//    private News news;


    @Builder
    public History(Client client) {
        this.client = client;
    }
}