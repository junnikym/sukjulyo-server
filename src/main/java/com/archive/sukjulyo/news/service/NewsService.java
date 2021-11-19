package com.archive.sukjulyo.news.service;

import com.archive.sukjulyo.hashtag.service.HashtagService;
import com.archive.sukjulyo.news.domain.News;
import com.archive.sukjulyo.news.dto.NewsCreateDTO;
import com.archive.sukjulyo.news.repository.NewsRepository;
import com.archive.sukjulyo.util.enums.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
	private final NewsRepository newsRepository;

	private final HashtagService hashtagService;

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
}
