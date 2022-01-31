package com.archive.sukjulyo.hashtag.controller;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.HashtagCreateDTO;
import com.archive.sukjulyo.hashtag.dto.HashtagFreqRequestDTO;
import com.archive.sukjulyo.hashtag.service.HashtagService;

import com.archive.sukjulyo.util.enums.Period;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hashtag")
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;

    /**
     * Select Hashtags
     *
     * @param pageable : Pageable Object
     * @param q : Query for search hashtag
     * @return Hashtag List
     */
    @GetMapping()
    public ResponseEntity selectHashtag(
            final Pageable pageable,
            @RequestParam(required = false) String q
    ) {
        if(q == null)
            return ResponseEntity.ok( hashtagService.selectHashtagList(pageable) );

        return ResponseEntity.ok( hashtagService.selectSearchedHashtagList(q, pageable) );
    }

    /**
     * Select hashtag's frequency registed between start_t and end_t
     *
     * @param period : wanna get rankings by period
     *               or get n-th largest frequency, set this param.
     *               If so, return result per parameter.
     *
     * @param dto : DTO for request Hashtag frequency
     *      limit - limit number of hashtag
     *      start - Time to hashtags started being registered
     *      end - Hashtags registered until this time
     *
     * @return Hashtag and Frequency
     */
    @GetMapping(value = "freq")
    public ResponseEntity selectHashtag(
            @RequestParam(required = false) Period period,
            HashtagFreqRequestDTO dto
    ) {
        switch(period) {
            case DAY:
                return ResponseEntity.ok( hashtagService.selectHashtagFreqNth(period, dto) );
            default:
                return ResponseEntity.ok( hashtagService.selectHahstagFreq(dto) );
        }
    }


    /**
     * Create new Hashtag
     *
     * @param dto : DTO for create hashtag
     * @return created hashtag
     */
    @PostMapping()
    public ResponseEntity createHashtag(
            @RequestBody HashtagCreateDTO dto
    ) {
        return ResponseEntity.ok(hashtagService.createHashtag(dto));
    }


    /**
     * Update hashtag's contents
     *
     * @param id : hashtag' id
     * @param dto : DTO for upfindHashtagFreqByDatedate hashtag
     * @return Updated entity
     */
    @PutMapping()
    public ResponseEntity updateHashtag(
            @RequestParam(required = false) Long id,
            @RequestBody HashtagCreateDTO dto
    ) throws Exception {
        return ResponseEntity.ok(hashtagService.updateHashtag(id, dto));
    }

    /**
     * Delete Hashatg
     *
     * @param id : hashtag's id
     * @return
     */
    @DeleteMapping()
    public ResponseEntity deleteHashtag(
            @RequestParam(required = false) Long id
    ) {
        hashtagService.deleteHashtag(id);
        return ResponseEntity.ok().build();
    }

}