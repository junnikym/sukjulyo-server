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
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
     * @return hash tags
     */
    public List<ClientHashtag> selectClientHashtag(Long id) {
        return clientHashtagRepository
                .findAllByClient(clientRepository.getById(id))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any target client's hashtags"
                ));
    }

    /**
     * Create a new column which is user's preferred hashtags
     *
     * @param dto : client hashtag create dto
     * @return ClientHashtag entity
     */
    public ClientHashtag saveClientHashtag(ClientHashtagCreateDTO dto) {

        AllRounder all = this.findByClientAndHashtag(dto.getClientId(), dto.getHashtag());

        if(all.clientHashtagOpt.isPresent()) {
            ClientHashtag clientHashtag = all.clientHashtagOpt.get();
            clientHashtag.setScore(dto.getScore());

            return clientHashtagRepository.save(clientHashtag);
        }
        else {
            return clientHashtagRepository.save(
                    ClientHashtag.builder()
                            .client(all.client)
                            .hashtag(all.hashtag)
                            .score(dto.getScore())
                            .build()
            );
        }
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



    @AllArgsConstructor
    private class AllRounder {
        public Client client;
        public Hashtag hashtag;
        public Optional<ClientHashtag> clientHashtagOpt;
    }

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
