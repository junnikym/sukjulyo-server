package com.archive.sukjulyo.hashtag.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Hashtag")
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;

    @GetMapping("/readHashtag")
    public List<Hashtag> getAllHashtag() {
        return this.hashtagService.readHashtags();
    }

//    @RequestMapping(value = "/hashtag")
//    public

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public long createHashtag(@PathVariable String tag) {
        return hashtagService.join(new Hashtag(tag, 1));
    }
}