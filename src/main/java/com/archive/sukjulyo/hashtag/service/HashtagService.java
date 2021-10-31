package com.archive.sukjulyo.hashtag.service;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.HashtagCreateDTO;
import com.archive.sukjulyo.hashtag.repository.HashtagRepository;
import com.archive.sukjulyo.util.PropertyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Hashtag createHashtag(HashtagCreateDTO dto) {
        return hashtagRepository.save(dto.toEntity());
    }

    //UPDATE Hashtag
    public Hashtag updateHashtag(Long id, HashtagCreateDTO dto) {
        Hashtag hashtag = hashtagRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target client"
                ));

        BeanUtils.copyProperties(dto, hashtag, PropertyUtil.getNullPropertyNames(dto));

        return hashtagRepository.save(hashtag);
    }

    //DELETE Hashtag
    public void deleteHashtag(Long id) {
        hashtagRepository.deleteById(id);
    }
}

