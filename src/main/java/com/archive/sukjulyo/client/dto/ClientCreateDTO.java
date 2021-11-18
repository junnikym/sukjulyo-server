package com.archive.sukjulyo.client.dto;
import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ClientCreateDTO implements Serializable {

    private Long id;

    public Client toEntity() {
        return Client.builder()
                .id(id)
                .build();
    }

}
