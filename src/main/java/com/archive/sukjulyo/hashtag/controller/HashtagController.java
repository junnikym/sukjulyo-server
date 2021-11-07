package com.archive.sukjulyo.hashtag.controller;

import com.archive.sukjulyo.hashtag.dto.HashtagCreateDTO;
import com.archive.sukjulyo.hashtag.dto.HashtagFreqRequestDTO;
import com.archive.sukjulyo.hashtag.service.HashtagService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/hashtag")
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping()
    public ResponseEntity selectHashtag(
            @RequestParam(required = false) Long id
    ) {
        if (id == null)
            return ResponseEntity.ok(hashtagService.selectHashtags());

        return ResponseEntity.ok(hashtagService.selectHashtag(id));
    }


    /**
     * Select hashtag's frequency registed between start_t and end_t
     *
     * @param dto : DTO for request Hashtag frequency
     *      limit - limit number of hashtag
     *      start - Time to hashtags started being registered
     *      end - Hashtags registered until this time
     * @return Hashtag and Frequency
     */
    @GetMapping(value = "freq")
    public ResponseEntity selectHashtag(HashtagFreqRequestDTO dto) {
        System.out.println(dto.toString());
        return ResponseEntity.ok(
                hashtagService.selectHahstagFreq(dto)
        );
    }



    @PostMapping()
    public ResponseEntity createHashtag(
            @RequestBody HashtagCreateDTO dto
    ) {
        return ResponseEntity.ok(hashtagService.createHashtag(dto));
    }


    @PutMapping()
    public ResponseEntity updateHashtag(
            @RequestParam(required = false) Long id,
            @RequestBody HashtagCreateDTO dto
    ) {
        return ResponseEntity.ok(hashtagService.updateHashtag(id, dto));
    }


    @DeleteMapping()
    public ResponseEntity deleteHashtag(
            @RequestParam(required = false) Long id
    ) {
        hashtagService.deleteHashtag(id);
        return ResponseEntity.ok().build();
    }

}