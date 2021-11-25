package com.archive.sukjulyo.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewsRecommendationResDTO {

	private List<Long> hashtags;

	public NewsRecommendationResDTO() {}

}
