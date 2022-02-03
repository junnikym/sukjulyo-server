package com.archive.sukjulyo.hashtag.domain;

import com.archive.sukjulyo.account.domain.Account;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Builder
public class HashtagPreference implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn
    private Account account;

    @ManyToOne(optional = false)
    @JoinColumn
    private Hashtag hashtag;

    @Column(nullable = false)
    private Integer score;
}