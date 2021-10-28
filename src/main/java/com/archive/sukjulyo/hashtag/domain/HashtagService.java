package com.archive.sukjulyo.hashtag.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public Long join(Hashtag hashtag) {
        validateDuplicateHashtag(hashtag);
        hashtagRepository.save(hashtag);
        return hashtag.getId();
    }

    private void validateDuplicateHashtag(Hashtag hashtag) {
        hashtagRepository.findByTag(hashtag.getTag())
                .ifPresent(m -> {throw new IllegalStateException("이미 존재하는 해시태그입니다.");});
    }

        //SELECT Hashtag
        public Hashtag selectHashtag (String tag)
        {
            Optional<Hashtag> hashtag = hashtagRepository.findByTag(tag);
            if (hashtag.isPresent()) {
                return hashtag.get();
            }
            throw new EntityNotFoundException("Cant find any Hashtag under given TAG");
        }
    public Optional<Hashtag> findOne(Long hashtagId) {
        return hashtagRepository.findById(hashtagId);
    }

        //SELECT HashtagAll
        public List<Hashtag> selectHashtags() {
            return hashtagRepository.findAll();
        }
    }
