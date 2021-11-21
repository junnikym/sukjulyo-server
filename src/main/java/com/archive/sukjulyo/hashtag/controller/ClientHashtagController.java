package com.archive.sukjulyo.hashtag.controller;

import com.archive.sukjulyo.hashtag.domain.ClientHashtag;
import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.ClientHashtagCreateDTO;
import com.archive.sukjulyo.hashtag.dto.ClientHashtagVO;
import com.archive.sukjulyo.hashtag.service.ClientHashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/hashtag/client")
@RequiredArgsConstructor
public class ClientHashtagController {

	private final ClientHashtagService clientHashtagService;

	@GetMapping("/has")
	public ResponseEntity hasHashtag(Principal principal) {
		return ResponseEntity.ok(
				clientHashtagService.hasClientHashatg(
						Long.parseLong((String)principal.getName()))
		);
	}

	@GetMapping("{clientId}")
	public ResponseEntity selectClientHashtag(
			final Pageable pageable,
			@PathVariable Long clientId
	) {
		return ResponseEntity.ok(
				clientHashtagService.selectClientHashtagInDetail(clientId, pageable));
	}

	@GetMapping()
	public ResponseEntity selectClientHashtag(
			@RequestParam(required = false) Long id,
			@RequestParam(required = true) Integer n_hashtag,
			@RequestParam(required = false) Integer n_client
	) {
		List<ClientHashtagVO> result = null;
		if(id != null) {
			result = clientHashtagService.selectClientHashtag(id, n_hashtag);
		}

		if(n_client != null) {

			if(id != null)
				n_client--;

			return ResponseEntity.ok(
				Stream.concat(
					result.stream(),
					clientHashtagService.selectClientHashtagRandomly(Arrays.asList(id), n_client, n_hashtag).stream()
				).collect(Collectors.toList())
			);
		}

		return ResponseEntity.ok(result);
	}

	@PostMapping()
	public ResponseEntity createClientHashtag(
			@RequestBody() List<ClientHashtagCreateDTO> data,
			Principal principal
	) {
		return ResponseEntity.ok(
				clientHashtagService.createClientHashtag(
						Long.parseLong((String)principal.getName()), data)
		);
	}
}
