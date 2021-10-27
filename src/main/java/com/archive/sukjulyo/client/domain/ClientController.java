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

    @GetMapping("/readAllClient")
    public ResponseEntity readClient(@RequestParam(required = false) Long id) {
        System.out.println("readClient");
        if (id == null)
            return ResponseEntity.ok(clientService.readClients());
        return ResponseEntity.ok(clientService.readClient(id));
    }

    @GetMapping("/readClient/{refreshtoken}")
    public ResponseEntity readClient(@PathVariable String refreshtoken) {
        return ResponseEntity.ok(clientService.readClient(refreshtoken));
    }

    @GetMapping("create/domain")
    public ResponseEntity createClient(@RequestBody ClientCreationRequest request) {
        return ResponseEntity.ok(clientService.createClient(request));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteClient (@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

}
