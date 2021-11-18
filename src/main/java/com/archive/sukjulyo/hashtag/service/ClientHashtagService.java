package com.archive.sukjulyo.hashtag.service;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.client.repository.ClientRepository;
import com.archive.sukjulyo.hashtag.domain.ClientHashtag;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.ClientHashtagCreateDTO;
import com.archive.sukjulyo.hashtag.dto.ClientHashtagDeleleDTO;
import com.archive.sukjulyo.hashtag.repository.ClientHashtagRepository;
import com.archive.sukjulyo.hashtag.repository.HashtagRepository;
import com.archive.sukjulyo.util.PropertyUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientHashtagService {

    private final HashtagRepository hashtagRepository;
    private final ClientHashtagRepository clientHashtagRepository;
    private final ClientRepository clientRepository;

    /**
     * Select client's preferred hashtag by client's pk id
     *
     * @param id : client's pk id
     * @param pageable : Pageable object
     * @return hash tags
     */
    public List<ClientHashtag> selectClientHashtag(Long id, Pageable pageable) {
        var client = clientRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target client"
                ));

        return clientHashtagRepository
                .findAllByClient(client, pageable)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target client's hashtags"
                ));
    }

    /**
     * Select client has hashatg
     */
    public Boolean hasClientHashatg(Long id) {
        return clientHashtagRepository.existsByClientId(id);
    }

    /**
     * Create hashatags data that the user prefers
     *
     * @param clientId : client's PK id
     * @param dtos : list of DTO for create client tag
     * @return created ClientHashtag list
     */
    public List<ClientHashtag> createClientHashtag(long clientId, List<ClientHashtagCreateDTO> dtos) {

        Client client = clientRepository
                .findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find target client"));

        List<ClientHashtag> result = new ArrayList<>();

        for(var it : dtos) {
            Hashtag hashtag = hashtagRepository
                    .findByTag(it.getHashtag())
                    .orElseThrow(() -> new IllegalArgumentException("Can't find target hashtag"));

            result.add(
                clientHashtagRepository.save(
                    ClientHashtag.builder()
                        .client(client)
                        .hashtag(hashtag)
                        .score(it.getScore())
                        .build()
                )
            );
        }

        return result;
    }


    /**
     * Delete the client preferred hashtag
     *
     * @param dto : DTO for delele ClientHashtag
     */
    public void deleteClientHashtag(ClientHashtagDeleleDTO dto) {
        ClientHashtag ent = this.findByClientAndHashtag(dto.getClientId(), dto.getHashtag())
                .clientHashtagOpt
                .orElseThrow(() -> new IllegalArgumentException("Can't find target client"));

        clientHashtagRepository.delete(ent);
    }


    /**
     * A class with all the entities required to find ClientHashtag
     */
    @AllArgsConstructor
    private class AllRounder {
        public Client client;
        public Hashtag hashtag;
        public Optional<ClientHashtag> clientHashtagOpt;
    }


    /**
     * Find out if the client has a hashtag.
     *
     * @param clientId : client's PK id
     * @param tag : hashtag
     * @return AllRounder class with found entities
     */
    private AllRounder findByClientAndHashtag(Long clientId, String tag) {
        Client client = clientRepository
                .findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find target client"));

        Hashtag hashtag = hashtagRepository
                .findByTag(tag)
                .orElseThrow(() -> new IllegalArgumentException("Can't find target hashtag"));

        return new AllRounder (
                client,
                hashtag,
                clientHashtagRepository.findByClientAndHashtag(client, hashtag)
        );
    }

}
