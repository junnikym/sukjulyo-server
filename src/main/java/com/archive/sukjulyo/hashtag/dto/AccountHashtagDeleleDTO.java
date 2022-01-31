package com.archive.sukjulyo.hashtag.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class AccountHashtagDeleleDTO {

	@NotNull
	private Long accountId;

	@NotNull
	private String Hashtag;

}
