package com.archive.sukjulyo.clientHashtag.service;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.client.repository.ClientRepository;
import com.archive.sukjulyo.clientHashtag.domain.ClientHashtag;
import com.archive.sukjulyo.clientHashtag.dto.ClientHashtagCreateDTO;
import com.archive.sukjulyo.clientHashtag.repository.ClientHashtagRepository;
import com.archive.sukjulyo.hashtag.repository.HashtagRepository;
import com.archive.sukjulyo.util.PropertyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientHashtagService {

    private final HashtagRepository hashtagRepository;
    private final ClientHashtagRepository clientHashtagRepository;
    private final ClientRepository clientRepository;

    //SELECT ClientHashtag
    public ClientHashtag selectClientHashtag(Long id) {
        return clientHashtagRepository
                .findByClient(clientRepository.getById(id))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target client's hashtags"
                ));
    }

    //CREATE ClientHashtag
    public ClientHashtag createClientHashtag(ClientHashtagCreateDTO dto) {
        dto.setClient(
                clientRepository
                        .findById(dto.getClientId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Can't find target client"
                        ))
        );

        dto.setHashtag(
                hashtagRepository
                        .findById(dto.getHashtagId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Can't find target client"
                        ))
        );

        return clientHashtagRepository.save(dto.toEntity());
    }

    //UPDATE ClientHashtag
    public ClientHashtag updateClientHashtag(Long id, ClientHashtagCreateDTO dto) {
        ClientHashtag clientHashtag = clientHashtagRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target client's hashtags"
                ));

        BeanUtils.copyProperties(dto, clientHashtag, PropertyUtil.getNullPropertyNames(dto));

        return clientHashtagRepository.save(clientHashtag);
    }

    //DELETE ClientHashtag
    public void deleteClientHashtag(Long id) {
        clientHashtagRepository.deleteById(id);
    }
}
