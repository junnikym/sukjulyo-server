package com.archive.sukjulyo.client.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@DynamicUpdate
public class Client {

	@Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ROLE_USER'")
    private Role role;

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN;
    }

}

