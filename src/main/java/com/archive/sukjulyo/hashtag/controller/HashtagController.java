package com.archive.sukjulyo.hashtag.controller;

import com.archive.sukjulyo.hashtag.domain.ClientHashtag;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.ClientHashtagCreateDTO;
import com.archive.sukjulyo.hashtag.dto.HashtagCreateDTO;
import com.archive.sukjulyo.hashtag.dto.HashtagFreqRequestDTO;
import com.archive.sukjulyo.hashtag.service.ClientHashtagService;
import com.archive.sukjulyo.hashtag.service.HashtagService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
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
    public List<Hashtag> selectHashtag(
            final Pageable pageable,
            @RequestParam(required = false) String q
    ) {
        if(q == null)
            return hashtagService.selectHashtagList(pageable);

        return hashtagService.selectSearchedHashtagList(q, pageable);
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
            @RequestParam(required = false) String period,
            HashtagFreqRequestDTO dto
    ) {
        switch(period.toLowerCase()) {
            case "day":
                return ResponseEntity.ok( hashtagService.selectHashtagFreqNth(dto) );
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
    ) {
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