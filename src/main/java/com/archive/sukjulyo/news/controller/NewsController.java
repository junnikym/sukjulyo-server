package com.archive.sukjulyo.news.controller;

import com.archive.sukjulyo.news.domain.News;
import com.archive.sukjulyo.news.dto.NewsCreateDTO;
import com.archive.sukjulyo.news.dto.NewsRecommendationResDTO;
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

import java.util.List;

@RestController
@RequestMapping(value = "/news")
@RequiredArgsConstructor
public class NewsController {

	private final NewsService newsService;

	private final RestTemplate restTemplate;

	@Value("${ai-app.entry}")
	private String aiAppUrl;

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
		String clientId = auth.getName();
		if(clientId.equals("anonymousUser"))
			return ResponseEntity.status(401).build();

		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		final HttpEntity<?> entity = new HttpEntity<>(headers);
		var res = restTemplate.exchange(
				(this.aiAppUrl+"/r/"+clientId),
				HttpMethod.GET,
				entity,
				NewsRecommendationResDTO.class
		).getBody();

		if(res == null)
			return ResponseEntity.internalServerError().build();

		return ResponseEntity.ok(newsService.selectAllNewsById(res.getHashtags()));
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
		newsService.deleteClient(id);
		return ResponseEntity.ok().build();
	}
}