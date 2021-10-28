package com.archive.sukjulyo.liked.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/liked")
@RequiredArgsConstructor
public class LikedController {
    private final LikedService likedService;

    @GetMapping("/select/{id}")
    public ResponseEntity selectLiked(@PathVariable Long id) {
        return ResponseEntity.ok(likedService.selectLiked(id));
    }

    @PostMapping("/create")
    public ResponseEntity createLiked(@RequestBody LikedCreationRequest request) {
        return ResponseEntity.ok(likedService.createLiked(request));
    }

    @PutMapping("/update")
    public ResponseEntity updateLiked(Long id, @RequestBody LikedCreationRequest request) {
        return ResponseEntity.ok(likedService.updateLiked(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteLiked (@PathVariable Long id) {
        likedService.deleteLiked(id);
        return ResponseEntity.ok().build();
    }
}
