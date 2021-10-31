package com.archive.sukjulyo.clientHashtag.controller;


import com.archive.sukjulyo.clientHashtag.dto.ClientHashtagCreateDTO;
import com.archive.sukjulyo.clientHashtag.service.ClientHashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/client/hash")
@RequiredArgsConstructor
public class ClientHashtagController {
    private final ClientHashtagService clientHashtagService;

    @GetMapping()
    public ResponseEntity selectClientHashtag(
            @RequestParam(required = false) Long id
    ) {
        return ResponseEntity.ok(clientHashtagService.selectClientHashtag(id));
    }

    @PostMapping()
    public ResponseEntity createClientHashtag(
            @RequestBody ClientHashtagCreateDTO dto
    ) {
        return ResponseEntity.ok(clientHashtagService.createClientHashtag(dto));
    }

    @PutMapping()
    public ResponseEntity updateClientHashtag(
            @RequestParam(required = false) Long id,
            @RequestBody ClientHashtagCreateDTO dto
    ) {
        return ResponseEntity.ok(clientHashtagService.updateClientHashtag(id, dto));
    }

    @DeleteMapping()
    public ResponseEntity deleteClientHashtag (
            @RequestParam(required = false) Long id
    ) {
        clientHashtagService.deleteClientHashtag(id);
        return ResponseEntity.ok().build();
    }
}