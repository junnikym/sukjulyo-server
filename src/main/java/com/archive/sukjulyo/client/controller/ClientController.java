package com.archive.sukjulyo.client.controller;

import com.archive.sukjulyo.client.dto.ClientCreateDTO;
import com.archive.sukjulyo.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/client")
@RequiredArgsConstructor
@Lazy
public class ClientController {
    private final ClientService clientService;

    @GetMapping()
    public ResponseEntity selectClient(@RequestParam(required = false) Long id) {
        if (id == null)
            return ResponseEntity.ok(clientService.selectClientList());
        return ResponseEntity.ok(clientService.selectClient(id));
    }

    @GetMapping("/{refreshToken}")
    public ResponseEntity selectClient(@PathVariable String refreshToken) {
        return ResponseEntity.ok(clientService.selectClient(refreshToken));
    }

    @PostMapping()
    public ResponseEntity createClient(@RequestBody ClientCreateDTO dto) {
        return ResponseEntity.ok(clientService.createClient(dto));
    }

    @PutMapping()
    public ResponseEntity updateClient(
            @RequestParam(required = false) Long id,
            @RequestBody ClientCreateDTO dto
    ) {
        return ResponseEntity.ok(clientService.updateClient(id, dto));
    }

    @DeleteMapping()
    public ResponseEntity deleteClient (@RequestParam(required = false) Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}

