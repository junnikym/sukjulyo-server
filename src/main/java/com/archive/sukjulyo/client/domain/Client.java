package com.archive.sukjulyo.client.domain;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
    private Long id;

    @Column(length = 128, nullable = true)
    private String refreshToken;

    @Column(length = 8, nullable = true)
    private String ageRange;

    @Column(length = 8, nullable = true)
    private String gender;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ROLE_USER'")
    private Role role;

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN;
    }

}

