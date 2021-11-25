package com.archive.sukjulyo.news.dto;

import com.archive.sukjulyo.news.domain.News;
import com.archive.sukjulyo.util.interfaces.DtoInterface;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class NewsUpdateDTO implements DtoInterface<News> {

	private String link;

	private String summary;

	public News toEntity() {
		return News.builder()
				.link(this.link)
				.summary(this.summary)
				.build();
	}

}
