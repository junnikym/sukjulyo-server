package com.archive.sukjulyo.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewsRecommendFromPyDTO {

	private List<Long> newsIds;

	private List<String> hashtags;

	public NewsRecommendFromPyDTO () {}

}
