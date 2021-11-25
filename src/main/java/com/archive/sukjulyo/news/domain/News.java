package com.archive.sukjulyo.news.domain;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class News implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 32, nullable = false, updatable=false)
	private String corp;

	@Column(length = 128, nullable = false, updatable=false)
	private String title;

	@Column(length = 512, nullable = true, unique = true, updatable=false)
	private String link;

	@Column(columnDefinition = "TEXT", nullable = true, updatable=false)
	private String description;

	@Column(columnDefinition = "TEXT", nullable = true)
	private String summary;

	@Column(length = 64, nullable = true, updatable=false)
	private String author;

	@Column(nullable = false, updatable = false)
	private LocalDateTime pubDate;

	@ManyToMany
	@JoinTable(
			name="news_hashtag",
			joinColumns=@JoinColumn(name = "news_fk"),
			inverseJoinColumns=@JoinColumn(name = "hashatag_fk")
	)
	@JsonManagedReference
	private List<Hashtag> hashtags = new ArrayList<>();

}
