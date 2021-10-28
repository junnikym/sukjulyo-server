package com.archive.sukjulyo.hashtag.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hashtag")
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;

    @PostMapping("/create")
    public ResponseEntity createHashtag(@RequestBody HashtagCreationRequest request) {
        return ResponseEntity.ok(hashtagService.createHashtag(request));
    }

    @GetMapping("/select")
    public ResponseEntity selectHashtag(@RequestParam(required = false) Long id) {
        if (id == null)
            return ResponseEntity.ok(hashtagService.selectHashtags());
        return ResponseEntity.ok(hashtagService.selectHashtag(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateHashtag(@RequestBody HashtagCreationRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(hashtagService.updateHashtag(request, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteHashtag(@PathVariable Long id) {
        hashtagService.deleteHashtag(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}