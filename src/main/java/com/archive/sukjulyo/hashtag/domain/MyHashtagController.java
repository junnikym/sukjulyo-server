package com.archive.sukjulyo.hashtag.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/MyHashtag")
@RequiredArgsConstructor
public class MyHashtagController {
    private final MyHashtagService myhashtagService;

    @GetMapping("/getMyHashtag/{id}")
    public MyHashtag getMyHashtag(@PathVariable Long id) {
        return this.myhashtagService.readMyHashtag(id);
    }
}