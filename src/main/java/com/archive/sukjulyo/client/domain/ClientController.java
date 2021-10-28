package com.archive.sukjulyo.client.domain;

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

    @GetMapping("/selectAll")
    public ResponseEntity selectClient(@RequestParam(required = false) Long id) {
        if (id == null)
            return ResponseEntity.ok(clientService.selectClients());
        return ResponseEntity.ok(clientService.selectClient(id));
    }

    @GetMapping("/select/{refreshtoken}")
    public ResponseEntity selectClient(@PathVariable String refreshtoken) {
        return ResponseEntity.ok(clientService.selectClient(refreshtoken));
    }

    @PostMapping("/create")
    public ResponseEntity createClient(@RequestBody ClientCreationRequest request) {
        return ResponseEntity.ok(clientService.createClient(request));
    }

    @PostMapping("/update")
    public ResponseEntity updateClient(Long id, @RequestBody ClientCreationRequest request) {
        return ResponseEntity.ok(clientService.updateClient(id, request));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteClient (@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

}

