package com.archive.sukjulyo.news.controller;

import com.archive.sukjulyo.news.dto.NewsCreateDTO;
import com.archive.sukjulyo.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/news")
@RequiredArgsConstructor
public class NewsController {
	private final NewsService newsService;

	@PostMapping()
	public ResponseEntity createNews (@RequestBody NewsCreateDTO dto) {
		return ResponseEntity.ok(newsService.createNews(dto));
	}

	@GetMapping("/exists")
	public ResponseEntity existsNews (@RequestParam(required = true) String link) {
		return ResponseEntity.ok(newsService.existsNews(link));
	}

	@DeleteMapping()
	public ResponseEntity deleteNews (@RequestParam(required = false) Long id) {
		newsService.deleteClient(id);
		return ResponseEntity.ok().build();
	}
}