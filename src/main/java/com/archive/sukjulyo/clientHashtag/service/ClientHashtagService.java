package com.archive.sukjulyo.clientHashtag.service;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.client.repository.ClientRepository;
import com.archive.sukjulyo.clientHashtag.domain.ClientHashtag;
import com.archive.sukjulyo.clientHashtag.dto.ClientHashtagCreationRequest;
import com.archive.sukjulyo.clientHashtag.repository.ClientHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientHashtagService {

    ClientHashtagRepository clientHashtagRepository;
    ClientRepository clientRepository;

    //SELECT ClientHashtag
    public ClientHashtag selectClientHashtag(Long id) {
        return clientHashtagRepository
                .findByClient(clientRepository.getById(id))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target client's hashtags"
                ));
    }

    //CREATE ClientHashtag
    public ClientHashtag createClientHashtag(ClientHashtagCreationRequest clientHashtag) {
//        Client client = clientRepository
//                .findById(clientHashtag.getClient().getId())
//                .orElseThrow(() -> new IllegalArgumentException(
//                        "Can't find target client"
//                ));

        return clientHashtagRepository.save(clientHashtag.toEntity());
    }

    //UPDATE ClientHashtag
    public ClientHashtag updateClientHashtag(Long id, ClientHashtagCreationRequest dto) {
        ClientHashtag optionalClientHashtag = clientHashtagRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target client's hashtags"
                ));

        // fuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuck
        ClientHashtag clientHashtag = dto.toEntity();

        return clientHashtagRepository.save(clientHashtag);
    }

    //DELETE ClientHashtag
    public void deleteClientHashtag(Long id) {
        clientHashtagRepository.deleteById(id);
    }
}
