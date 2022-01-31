package com.archive.sukjulyo.account.controller;

import com.archive.sukjulyo.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor
@Lazy
public class AccountController {

    private final AccountService accountService;

//    @GetMapping()
//    public ResponseEntity selectAccount(@RequestParam(required = true) Long id) {
//        return ResponseEntity.ok(accountService.selectAccount(id));
//    }

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

//    @PostMapping()
//    public ResponseEntity createAccount(@RequestBody AccountCreateDTO dto) {
//        return ResponseEntity.ok(accountService.createAccount(dto));
//    }

//    @PutMapping()
//    public ResponseEntity updateAccount(
//            @RequestParam(required = false) Long id,
//            @RequestBody AccountCreateDTO dto
//    ) {
//        return ResponseEntity.ok(accountService.updateAccount(id, dto));
//    }

    @DeleteMapping()
    public ResponseEntity deleteAccount (@RequestParam(required = false) Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
}

