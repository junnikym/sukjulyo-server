package com.archive.sukjulyo.client.service;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.client.dto.ClientCreateDTO;
import com.archive.sukjulyo.client.repository.ClientRepository;
import com.archive.sukjulyo.util.PropertyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Lazy
public class ClientService {
    private final ClientRepository clientRepository;

    //SELECT Client
    public Client selectClient(Long id) {
        Client client = clientRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Can't find target client"
                ));

        return client;
    }

    //SELECT ALL Clients
    public List<Client> selectClientList() {
        return clientRepository.findAll();
    }

    //SELECT Client By Refresh_token
    public Client selectClient(String refreshToken) {
        Client client = clientRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target client's refresh token"
                ));

        return client;
    }

    //CREATE Client
    public Client createClient(ClientCreateDTO dto) {
        return clientRepository.save(dto.toEntity());
    }

    //UPDATE Client
    public Client updateClient(Long id, ClientCreateDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target client"
                ));

        BeanUtils.copyProperties(dto, client, PropertyUtil.getNullPropertyNames(dto));

        return clientRepository.save(client);
    }

    //DELETE Client
    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    }
}
