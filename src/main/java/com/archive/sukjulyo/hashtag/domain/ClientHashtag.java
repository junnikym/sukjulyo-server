package com.archive.sukjulyo.hashtag.domain;

import com.archive.sukjulyo.client.domain.Client;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class ClientHashtag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @Column(nullable = false)
    private Integer score;
}