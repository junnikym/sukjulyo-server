package com.archive.sukjulyo.news.dto;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.news.domain.News;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class NewsCreateDTO implements Serializable {

	private String corp;
	private String title;
	private String link;
	private String description;
	private String author;

	private List<String> hashtags;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime pubDate;

	public News toEntity(List<Hashtag> hashtagEntities) {

		return News.builder()
				.corp(this.corp)
				.title(this.title)
				.link(this.link)
				.description(this.description)
				.author(this.author)
				.pubDate(this.pubDate)
				.hashtags(hashtagEntities)
				.build();
	}

}
