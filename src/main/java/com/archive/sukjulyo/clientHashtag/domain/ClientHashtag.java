package com.archive.sukjulyo.clientHashtag.domain;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@DynamicInsert
public class ClientHashtag {
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
    private Integer score;
}