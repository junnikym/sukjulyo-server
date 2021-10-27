package com.archive.sukjulyo.hashtag.domain;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.client.domain.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyHashtagService {

    MyHashtagRepository myHashtagRepository;
    ClientRepository clientRepository;

    //SELECT MyHashtag
    public MyHashtag readMyHashtag(Client client) {
        Optional<MyHashtag> myHashtag = myHashtagRepository.findByClient(client);
        if (myHashtag.isPresent())
            return myHashtag.get();
        throw new EntityNotFoundException("Cant find any MyHashtag in this Client");
    }

    //CREATE MyHashtag
    public MyHashtag createMyHashtag(MyHashtagCreationRequest myhashtag) {
        Optional<Client> client = clientRepository.findById(myhashtag.getClient().getId());
        if (!client.isPresent()) {
            throw new EntityNotFoundException(("Client Not Found"));
        }

        MyHashtag myhashtagToCreate = new MyHashtag();
        BeanUtils.copyProperties(myhashtag, myhashtagToCreate);
        myhashtagToCreate.setClient(client.get());
        return myHashtagRepository.save(myhashtagToCreate);
    }
}
