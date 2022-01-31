package com.archive.sukjulyo.liked.controller;

import com.archive.sukjulyo.liked.dto.LikedCreationDTO;
import com.archive.sukjulyo.liked.service.LikedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/liked")
@RequiredArgsConstructor
public class LikedController {

    private final LikedService likedService;

    @GetMapping()
    public ResponseEntity selectLiked(
            @RequestParam(required = false) Long id
    ) {
        return ResponseEntity.ok(likedService.selectLiked(id));
    }

    @PostMapping()
    public ResponseEntity createLiked(
            @RequestBody LikedCreationDTO request
    ) {
        return ResponseEntity.ok(likedService.createLiked(request));
    }

    @PutMapping()
    public ResponseEntity updateLiked(
            @RequestParam(required = false) Long id,
            @RequestBody LikedCreationDTO request
    ) throws Exception {
        return ResponseEntity.ok(likedService.updateLiked(id, request));
    }

    @DeleteMapping()
    public ResponseEntity deleteLiked (
            @RequestParam(required = false) Long id
    ) {
        likedService.deleteLiked(id);
        return ResponseEntity.ok().build();
    }
}
