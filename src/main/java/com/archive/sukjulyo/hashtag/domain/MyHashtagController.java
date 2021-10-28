package com.archive.sukjulyo.hashtag.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/MyHashtag")
@RequiredArgsConstructor
public class MyHashtagController {
    private final MyHashtagService myhashtagService;

    @GetMapping("/select/{id}")
    public ResponseEntity selectMyHashtag(@PathVariable Long id) {
        return ResponseEntity.ok(myhashtagService.selectMyHashtag(id));
    }

    @PostMapping("/create")
    public ResponseEntity createMyHashtag(@RequestBody MyHashtagCreationRequest request) {
        return ResponseEntity.ok(myhashtagService.createMyHashtag(request));
    }

    @PutMapping("/update")
    public ResponseEntity updateMyHashtag(Long id, @RequestBody MyHashtagCreationRequest request) {
        return ResponseEntity.ok(myhashtagService.updateMyHashtag(id, request));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteMyHashtag (@PathVariable Long id) {
        myhashtagService.deleteMyHashtag(id);
        return ResponseEntity.ok().build();
    }
}