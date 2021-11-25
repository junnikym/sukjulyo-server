package com.archive.sukjulyo.news.controller;

import com.archive.sukjulyo.news.dto.NewsCreateDTO;
import com.archive.sukjulyo.news.dto.NewsUpdateDTO;
import com.archive.sukjulyo.util.enums.Period;
import com.archive.sukjulyo.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news")
@RequiredArgsConstructor
public class NewsController {
	private final NewsService newsService;

	@PostMapping()
	public ResponseEntity createNews (@RequestBody NewsCreateDTO dto) {
		return ResponseEntity.ok(newsService.createNews(dto));
	}

	@GetMapping()
	public ResponseEntity selectNews (
			final Pageable pageable,
			@RequestParam(required = true) Period period
	) {
		return ResponseEntity.ok(newsService.selectNewsByPeriod(period, pageable));
	}

	@PutMapping()
	public ResponseEntity updateNewsSummary (@RequestBody NewsUpdateDTO dto) {
		int n = newsService.updateNewsSummary(dto);
		switch (n) {
			case 0:
				return ResponseEntity.notFound().build();
			case 1:
				return ResponseEntity.ok().build();
			default:
				return ResponseEntity.badRequest().build();
		}
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