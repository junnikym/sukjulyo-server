package com.archive.sukjulyo.hashtag.domain;

import com.archive.sukjulyo.account.domain.Account;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class AccountHashtag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Account account;

    @ManyToOne(optional = false)
    private Hashtag hashtag;

    @Column(nullable = false)
    private Integer score;
}