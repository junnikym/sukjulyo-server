package com.archive.sukjulyo.hashtag.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class ClientHashtagDeleleDTO {

	@NotNull
	private Long clientId;

	@NotNull
	private String Hashtag;

}
