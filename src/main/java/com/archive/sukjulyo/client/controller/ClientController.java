package com.archive.sukjulyo.client.controller;

import com.archive.sukjulyo.client.dto.ClientCreateDTO;
import com.archive.sukjulyo.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/client")
@RequiredArgsConstructor
@Lazy
public class ClientController {
    private final ClientService clientService;

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/test_good")
    public String test_good() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "<html> "+auth.toString()+" </html>";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/test_bad")
    public String test_bad() {
        return "<html> bad </html>";
    }

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

//    @PostMapping()
//    public ResponseEntity createClient(@RequestBody ClientCreateDTO dto) {
//        return ResponseEntity.ok(clientService.createClient(dto));
//    }

//    @PutMapping()
//    public ResponseEntity updateClient(
//            @RequestParam(required = false) Long id,
//            @RequestBody ClientCreateDTO dto
//    ) {
//        return ResponseEntity.ok(clientService.updateClient(id, dto));
//    }

    @DeleteMapping()
    public ResponseEntity deleteClient (@RequestParam(required = false) Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}

