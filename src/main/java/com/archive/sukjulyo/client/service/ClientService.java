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

    /**
     * Select Client by client's primary key ID
     *
     * @param id : clinet's parimary key
     * @return Client entity
     */
    public Client selectClient(Long id) {
        return clientRepository
                .findById(id)
                .orElse(null);
    }


    /**
     * Select client and update data
     * if not exist client, Create new client
     *
     * @param client : Put in here depending on the function you want
     *               like this ( [update] -> client entity, [create] -> NULL )
     * @param dto : DTO for create client
     * @return Client Entity
     */
    public Client saveAndUpdateClient(Client client, ClientCreateDTO dto) {

        if(client == null)
            return clientRepository.save(dto.toEntity());

        BeanUtils.copyProperties(dto, client, PropertyUtil.getNullPropertyNames(dto));
        return clientRepository.save(client);
    }

    /**
     * Delete Client
     *
     * @param id : client's PK id
     */
    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    }
}
