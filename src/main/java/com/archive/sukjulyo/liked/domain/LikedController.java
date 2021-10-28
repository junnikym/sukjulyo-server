package com.archive.sukjulyo.liked.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/liked")
@RequiredArgsConstructor
public class LikedController {
    private final LikedService likedService;

    @GetMapping("/selectLiked/{id}")
    public ResponseEntity selectLiked(@PathVariable Long id) {
        return ResponseEntity.ok(likedService.selectLiked(id));
    }
}
