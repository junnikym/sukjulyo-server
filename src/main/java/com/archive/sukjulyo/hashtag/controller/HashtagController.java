package com.archive.sukjulyo.hashtag.controller;

import com.archive.sukjulyo.hashtag.dto.HashtagCreateDTO;
import com.archive.sukjulyo.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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