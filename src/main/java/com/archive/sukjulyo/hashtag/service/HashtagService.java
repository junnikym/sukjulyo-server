package com.archive.sukjulyo.hashtag.service;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.HashtagCreationRequest;
import com.archive.sukjulyo.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    //SELECT Hashtag
    public Hashtag selectHashtag(Long id) {
        return hashtagRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target client"
                ));
    }

    //SELECT ALL Hashtag
    public List<Hashtag> selectHashtags() {
        return hashtagRepository.findAll();
    }

    //CREATE Hashtag
    public Hashtag createHashtag(HashtagCreationRequest dto) {
        return hashtagRepository.save(dto.toEntity());
    }

    //UPDATE Hashtag
    public Hashtag updateHashtag(HashtagCreationRequest dto, Long id) {
        Hashtag optionalHashtag = hashtagRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target client"
                ));

        // fuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuck
        Hashtag hashtag = dto.toEntity();

        return hashtagRepository.save(hashtag);
    }

    //DELETE Hashtag
    public void deleteHashtag(Long id) {
        hashtagRepository.deleteById(id);
    }
}

