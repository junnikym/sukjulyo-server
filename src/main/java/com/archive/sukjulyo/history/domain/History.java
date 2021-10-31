package com.archive.sukjulyo.history.domain;

import com.archive.sukjulyo.client.domain.Client;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
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