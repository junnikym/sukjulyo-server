package com.archive.sukjulyo.news.controller;

import com.archive.sukjulyo.client.dto.ClientCreateDTO;
import com.archive.sukjulyo.client.service.ClientService;
import com.archive.sukjulyo.news.dto.NewsCreateDTO;
import com.archive.sukjulyo.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/news")
@RequiredArgsConstructor
public class NewsController {
	private final NewsService newsService;

	@PostMapping()
	public ResponseEntity createNews(@RequestBody NewsCreateDTO dto) {
		System.out.println("start create news - controller");

		return ResponseEntity.ok(newsService.createNews(dto));
	}

	@DeleteMapping()
	public ResponseEntity deleteNews (@RequestParam(required = false) Long id) {
		newsService.deleteClient(id);
		return ResponseEntity.ok().build();
	}
}