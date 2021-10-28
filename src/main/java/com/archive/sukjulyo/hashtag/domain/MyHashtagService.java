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
    public MyHashtag selectMyHashtag(Long id) {
        Optional<MyHashtag> myHashtag = myHashtagRepository.findByClient(clientRepository.getById(id));
        if (myHashtag.isPresent())
            return myHashtag.get();
        throw new EntityNotFoundException("Can not find any MyHashtag in this Client");
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

    //UPDATE MyHashtag
    public MyHashtag updateMyHashtag(Long id, MyHashtagCreationRequest request) {
        Optional<MyHashtag> optionalMyHashtag = myHashtagRepository.findById(id);
        if (!optionalMyHashtag.isPresent())
                throw new EntityNotFoundException("MyHashtag not present in the database");

        MyHashtag myHashtag = optionalMyHashtag.get();
        myHashtag.setClient(request.getClient());
        myHashtag.setHashtag(request.getHashtag());
        myHashtag.setScore(request.getScore());
        return myHashtagRepository.save(myHashtag);
    }

    //DELETE MyHashtag
    public void deleteMyHashtag(Long id) {
        myHashtagRepository.deleteById(id);
    }
}
