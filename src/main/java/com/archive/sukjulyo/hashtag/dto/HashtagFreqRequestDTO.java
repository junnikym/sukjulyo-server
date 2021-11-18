package com.archive.sukjulyo.hashtag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HashtagFreqRequestDTO implements Serializable {

	@NotNull
	private Integer limit = 25;

	private Integer offset = 0;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime start;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime end;

}
