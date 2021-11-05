package com.archive.sukjulyo.news.service;

import com.archive.sukjulyo.hashtag.service.HashtagService;
import com.archive.sukjulyo.news.domain.News;
import com.archive.sukjulyo.news.dto.NewsCreateDTO;
import com.archive.sukjulyo.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

		System.out.println("start create news");

		/**
		 * Find hashtags in DB,
		 * If hashtag does not exist, create it
		 -------------------------------------------------- */
		List<String> tags = new ArrayList<String>(dto.getHashtags());

		/**
		 * Save it
		 -------------------------------------------------- */
		return newsRepository.save(
				dto.toEntity(
						hashtagService.selectAndCreateHashtag(tags)
		));
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
