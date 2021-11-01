package com.archive.sukjulyo.history.controller;

import com.archive.sukjulyo.history.dto.HistoryCreationDTO;
import com.archive.sukjulyo.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping()
    public ResponseEntity selectHistory(
            @RequestParam(required = false) Long id
    ) {
        if (id == null)
            return ResponseEntity.ok(historyService.selectHistoryList());

        return ResponseEntity.ok(historyService.selectHistory(id));
    }

    @PostMapping()
    public ResponseEntity createHistory(
            @RequestBody HistoryCreationDTO request
    ) {
        return ResponseEntity.ok(historyService.createHistory(request));
    }

    @DeleteMapping()
    public ResponseEntity deleteHistory(
            @RequestParam(required = false) Long id
    ) {
        historyService.deleteHistory(id);
        return ResponseEntity.ok().build();
    }

}