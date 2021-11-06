package com.archive.sukjulyo.hashtag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HashtagFreqRequestDTO {

	@NotNull
	private Integer limit = 25;

	@NotNull
	private LocalDateTime startTime;

	@NotNull
	private LocalDateTime endTime;

}
