package com.archive.sukjulyo.news.service;

import com.archive.sukjulyo.hashtag.service.HashtagService;
import com.archive.sukjulyo.news.domain.News;
import com.archive.sukjulyo.news.dto.NewsCreateDTO;
import com.archive.sukjulyo.news.dto.NewsRecommendFromPyDTO;
import com.archive.sukjulyo.news.dto.NewsRecommendResDTO;
import com.archive.sukjulyo.news.dto.NewsUpdateDTO;
import com.archive.sukjulyo.news.repository.NewsRepository;
import com.archive.sukjulyo.util.PropertyUtil;
import com.archive.sukjulyo.util.enums.Period;
import com.archive.sukjulyo.util.interfaces.DtoInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
	private final NewsRepository newsRepository;

	private final HashtagService hashtagService;

	private final RestTemplate restTemplate;

	@Value("${ai-app.entry}")
	private String aiAppUrl;

	/**
	 * Create News
	 *
	 * @param dto : News Create DTO
	 * @return News Entity
	 */
	public News createNews(NewsCreateDTO dto) {

		/**
		 * Find hashtags in DB,
		 * If hashtag does not exist, create it
		 -------------------------------------------------- */
		List<String> tags = new ArrayList<String>(dto.getHashtags());

		/**
		 * Save it
		 -------------------------------------------------- */
		return newsRepository.save(
				dto.toEntity(hashtagService.selectAndCreateHashtag(tags)
		));
	}

	/**
	 * Select News List
	 *
	 * @param ids : News list which wanna find
	 * @return Selected news list
	 */
	public List<News> selectAllNewsById(List<Long> ids) {
		return newsRepository
				.findAllByIdIn(ids)
				.orElseThrow(() -> new IllegalArgumentException(
						"Can't find any target news"
				));
	}

	/**
	 * Select news from now until period that input
	 *
	 * @param period : period
	 * @return Selected news list
	 */
	public List<News> selectNewsByPeriod(Period period, Pageable pageable) {
		var duration = period.toDuration();
		if(duration == null)
			throw new IllegalArgumentException("wrong period");

		var end = LocalDateTime.now();
		var start = end.minusHours(duration.toHours());

		return newsRepository.findAllByPubDateBetween(start, end, pageable);
	}

	/**
	 * Update summary of news data
	 *
	 * @param dto : News update DTO
	 * @return Number of updated elements
	 */
	public int updateNewsSummary(NewsUpdateDTO dto) {
		try {
			return newsRepository.updateSummary(dto);
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Check if data is exist on the DB by news link
	 *
	 * @param newsLink : link of news
	 * @return Existence or not.
	 */
	public Boolean existsNews(String newsLink) {
		return newsRepository.existsByLink(newsLink);
	}

	/**
	 * Delete News
	 *
	 * @param id : News primary key ID
	 */
	public void deleteClient(Long id) {
		newsRepository.deleteById(id);
	}



	public NewsRecommendResDTO recommendNews(String clientId) {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		final HttpEntity<?> entity = new HttpEntity<>(headers);
		var res = restTemplate.exchange(
				(this.aiAppUrl+"/r/"+clientId),
				HttpMethod.GET,
				entity,
				NewsRecommendFromPyDTO.class
		).getBody();

		if(res == null)
			return null;

		return NewsRecommendResDTO.builder()
						.news( selectAllNewsById(res.getNewsIds()) )
						.hashtags( res.getHashtags() )
						.build();
	}
}
