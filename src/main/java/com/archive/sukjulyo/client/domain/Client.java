package com.archive.sukjulyo.client.domain;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@DynamicUpdate
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 128, nullable = false)
    private String refreshToken;

    @Column(length = 8, nullable = true)
    private String ageRange;

    @Column(length = 8, nullable = true)
    private String gender;

}

