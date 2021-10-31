package com.archive.sukjulyo.clientHashtag.controller;


import com.archive.sukjulyo.clientHashtag.dto.ClientHashtagCreationRequest;
import com.archive.sukjulyo.clientHashtag.service.ClientHashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ClientHashtag")
@RequiredArgsConstructor
public class ClientHashtagController {
    private final ClientHashtagService clientHashtagService;

    @GetMapping("/select/{id}")
    public ResponseEntity selectClientHashtag(@PathVariable Long id) {
        return ResponseEntity.ok(clientHashtagService.selectClientHashtag(id));
    }

    @PostMapping("/create")
    public ResponseEntity createClientHashtag(@RequestBody ClientHashtagCreationRequest dto) {
        return ResponseEntity.ok(clientHashtagService.createClientHashtag(dto));
    }

    @PutMapping("/update")
    public ResponseEntity updateClientHashtag(Long id, @RequestBody ClientHashtagCreationRequest dto) {
        return ResponseEntity.ok(clientHashtagService.updateClientHashtag(id, dto));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteClientHashtag (@PathVariable Long id) {
        clientHashtagService.deleteClientHashtag(id);
        return ResponseEntity.ok().build();
    }
}