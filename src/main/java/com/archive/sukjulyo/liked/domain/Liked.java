package com.archive.sukjulyo.liked.domain;

import com.archive.sukjulyo.account.domain.Account;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isLike;

    @CreationTimestamp
    private LocalDateTime create_at;

//    @ManyToOne
//    @JoinColumn(name = "news_id")
//    private News news;

}