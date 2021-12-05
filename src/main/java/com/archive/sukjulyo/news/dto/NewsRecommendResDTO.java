package com.archive.sukjulyo.news.dto;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.news.domain.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class NewsRecommendResDTO {

	private List<News> news;

	private List<String> hashtags;

}
