package com.archive.sukjulyo.history.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping("/create")
    public ResponseEntity createHistory(@RequestBody HistoryCreationRequest request) {
        return ResponseEntity.ok(historyService.createHistory(request));
    }

    @GetMapping("/select")
    public ResponseEntity selectHistory(@RequestParam(required = false) Long id) {
        if (id == null)
            return ResponseEntity.ok(historyService.selectHistorys());
        return ResponseEntity.ok(historyService.selectHistory(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteHistory(@PathVariable Long id) {
        historyService.deleteHistory(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}