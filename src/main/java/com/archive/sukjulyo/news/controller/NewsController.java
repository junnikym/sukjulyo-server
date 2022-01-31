package com.archive.sukjulyo.news.controller;

import com.archive.sukjulyo.news.dto.NewsCreateDTO;
import com.archive.sukjulyo.news.dto.NewsRecommendFromPyDTO;
import com.archive.sukjulyo.news.dto.NewsRecommendResDTO;
import com.archive.sukjulyo.news.dto.NewsUpdateDTO;
import com.archive.sukjulyo.util.enums.Period;
import com.archive.sukjulyo.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/news")
@RequiredArgsConstructor
public class NewsController {

	private final NewsService newsService;

	/**
	 * Create news data in DB
	 *
	 * @param dto : DTO for news create
	 * @return created news
	 */
	@PostMapping()
	public ResponseEntity createNews (@RequestBody NewsCreateDTO dto) {
		return ResponseEntity.ok(newsService.createNews(dto));
	}

	/**
	 * Select all news
	 *
	 * @param pageable : pageable object
	 * @param period : Looking for news from today to period
	 * @return Selected news
	 */
	@GetMapping()
	public ResponseEntity selectNews (
			final Pageable pageable,
			@RequestParam(required = true) Period period
	) {
		return ResponseEntity.ok(newsService.selectNewsByPeriod(period, pageable));
	}

	/**
	 * Select the recommended news
	 *
	 * @return Selected news list
	 */
	@GetMapping("/recommendation")
	public ResponseEntity newsRecommendations() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String accountId = auth.getName();
		if(accountId.equals("anonymousUser"))
			return ResponseEntity.status(401).build();

		var result = newsService.recommendNews(accountId);

		if(result == null)
			return ResponseEntity.internalServerError().build();

		return ResponseEntity.ok(result);
	}

	/**
	 * Update news summary
	 *
	 * @param dto : DTO for news update
	 * @return Dividing success or not into status_code.
	 */
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

	/**
	 * Find out is exists link or not
	 *
	 * @param link : News link
	 * @return exists (T or F)
	 */
	@GetMapping("/exists")
	public ResponseEntity existsNews (@RequestParam(required = true) String link) {
		return ResponseEntity.ok(newsService.existsNews(link));
	}

	/**
	 * Delete News by id
	 *
	 * @param id : News id
	 * @return
	 */
	@DeleteMapping()
	public ResponseEntity deleteNews (@RequestParam(required = false) Long id) {
		newsService.deleteNews(id);
		return ResponseEntity.ok().build();
	}
}