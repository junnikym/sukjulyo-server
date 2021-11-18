package com.archive.sukjulyo.hashtag.controller;

import com.archive.sukjulyo.hashtag.domain.ClientHashtag;
import com.archive.sukjulyo.hashtag.dto.ClientHashtagCreateDTO;
import com.archive.sukjulyo.hashtag.service.ClientHashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/hashtag/client")
@RequiredArgsConstructor
public class ClientHashtagController {

	private final ClientHashtagService clientHashtagService;

	@GetMapping("/has")
	public Boolean hasHashtag(Principal principal) {
		return clientHashtagService.hasClientHashatg(
				Long.parseLong((String)principal.getName()));
	}

	@PostMapping()
	public List<ClientHashtag> createClientHashtag(
			@RequestBody() List<ClientHashtagCreateDTO> data,
			Principal principal
	) {
		return clientHashtagService.createClientHashtag(
				Long.parseLong((String)principal.getName()), data );
	}
}
