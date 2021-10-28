package com.archive.sukjulyo.hashtag.domain;

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
        Optional<Hashtag> hashtag = hashtagRepository.findById(id);
        if (hashtag.isPresent())
            return hashtag.get();

        throw new EntityNotFoundException("Cant find any hashtag under given ID");
    }

    //SELECT ALL Hashtag
    public List<Hashtag> selectHashtags() {
        return hashtagRepository.findAll();
    }

    //CREATE Hashtag
    public Hashtag createHashtag(HashtagCreationRequest request) {
        Hashtag hashtag = new Hashtag();
        BeanUtils.copyProperties(request, hashtag);
        return hashtagRepository.save(hashtag);
    }

    //DELETE Hashtag
    public void deleteHashtag(Long id) {
        hashtagRepository.deleteById(id);
    }

    //UPDATE Hashtag
    public Hashtag updateHashtag(HashtagCreationRequest request, Long id) {
        Optional<Hashtag> optionalHashtag = hashtagRepository.findById(id);
        if (!optionalHashtag.isPresent()) {
            throw new EntityNotFoundException("Hashtag not present in the database");
        }

        Hashtag hashtag = optionalHashtag.get();
        hashtag.setTag(request.getTag());
        hashtag.setPriority(request.getPriority());
        return hashtagRepository.save(hashtag);
    }
}

