package com.archive.sukjulyo.client.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Lazy
public class ClientService {
    private final ClientRepository clientRepository;

    //SELECT Client
    public Client selectClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent())
            return client.get();

        throw new EntityNotFoundException("Cant find any client under given ID");
    }

    //SELECT ALL Clients
    public List<Client> selectClients(){
        return clientRepository.findAll();
    }

    //SELECT Client By Refresh_token
    public Client selectClient(String refreshtoken) {
        Optional<Client> client = clientRepository.findByRefreshtoken(refreshtoken);
        if (client.isPresent()) {
            return  client.get();
        }
        throw new EntityNotFoundException("Cant find any Client under given refresh_token");
    }


    //CREATE Client
    public Client createClient(ClientCreationRequest request) {
        Client client = new Client();
        BeanUtils.copyProperties(request, client);
        return clientRepository.save(client);
    }

    //DELETE Client
    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    }
}
