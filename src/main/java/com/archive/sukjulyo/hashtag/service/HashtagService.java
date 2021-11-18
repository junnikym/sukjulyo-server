package com.archive.sukjulyo.hashtag.service;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.HashtagCreateDTO;
import com.archive.sukjulyo.hashtag.dto.HashtagFreqRequestDTO;
import com.archive.sukjulyo.hashtag.dto.HashtagFreqResponseVO;
import com.archive.sukjulyo.hashtag.dto.HashtagFreqTopNResponseVO;
import com.archive.sukjulyo.hashtag.repository.HashtagRepository;
import com.archive.sukjulyo.util.PropertyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    /**
     * Select Single Hashtag
     *
     * @param id : Hashtag's ID
     * @return Selected Hashtag
     */
    public Hashtag selectHashtag(Long id) {
        return hashtagRepository
                .findById(id)
                .orElseGet( ()->{ return null; } );
    }

    /**
     * Select All Hashtags
     *
     * @return All of Hashtag in DB
     */
    public List<Hashtag> selectHashtagList(Pageable pageable) {
        return hashtagRepository.findByIsNoiseIsNotNull(pageable);
    }

    public List<Hashtag> selectSearchedHashtagList(String query, Pageable pageable) {
        var result =  hashtagRepository.findByTagContaining(query, pageable);
        return result;
    }

    /**
     * Select hashtag's frequency registed between start_t and end_t
     *
     * @param dto : DTO for select hashtag's frequency
     * @return Hashtag and Frequency
     */
    public List<HashtagFreqResponseVO> selectHahstagFreq(
            HashtagFreqRequestDTO dto
    ) {
        return hashtagRepository.findHashtagFreqByDate(
                dto.getLimit(),
                dto.getOffset(),
                dto.getStart(),
                dto.getEnd()
        );
    }

    /**
     * Select hashtag's Top n-th frequency
     *
     * @param dto
     * @return
     */
    public List<HashtagFreqTopNResponseVO> selectHashtagFreqNth(
            HashtagFreqRequestDTO dto
    ) {
        return hashtagRepository.findHashtagFreqNth (
                dto.getLimit(),
                dto.getOffset(),
                dto.getStart(),
                dto.getEnd()
        );
    }


    /**
     * Save muliple strings as Hashtags
     *
     * @param tags : Strings to be used as tags
     * @return Saved hashtag entities
     */
    public List<Hashtag> selectAndCreateHashtag(List<String> tags) {
        List<Hashtag> result = new ArrayList<Hashtag>();

        for(String it : tags) {
            Hashtag hashtag = hashtagRepository
                    .findByTag(it)
                    .orElseGet( () ->{
                        return hashtagRepository.save(
                                Hashtag.builder()
                                    .tag(it)
                                    .news(null)
                                    .build()
                        );
                    });

            result.add(hashtag);
        }

        return result;
    }

    /**
     * Create Hashtag
     *
     * @param dto : DTO for Hashtage Create
     * @return Created Hashtag
     */
    public Hashtag createHashtag(HashtagCreateDTO dto) {
        return hashtagRepository.save(dto.toEntity());
    }

    /**
     * Update Hashtag
     *
     * @param id : Hashtag ID
     * @param dto : DTO for Update Hashtag
     * @return Updated Hashtag
     */
    public Hashtag updateHashtag(Long id, HashtagCreateDTO dto) {
        Hashtag hashtag = hashtagRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target client"
                ));

        BeanUtils.copyProperties(dto, hashtag, PropertyUtil.getNullPropertyNames(dto));

        return hashtagRepository.save(hashtag);
    }

    /**
     * Delete hashtag
     *
     * @param id : Hashtag's PK id
     */
    public void deleteHashtag(Long id) {
        hashtagRepository.deleteById(id);
    }
}

