package com.archive.sukjulyo.hashtag.controller;

import com.archive.sukjulyo.hashtag.dto.AccountHashtagCreateDTO;
import com.archive.sukjulyo.hashtag.dto.AccountHashtagVO;
import com.archive.sukjulyo.hashtag.service.AccountHashtagService;
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
@RequestMapping(value = "/hashtag/account")
@RequiredArgsConstructor
public class AccountHashtagController {

	private final AccountHashtagService accountHashtagService;

	@GetMapping("/has")
	public ResponseEntity hasHashtag(Principal principal) {
		return ResponseEntity.ok(
				accountHashtagService.hasAccountHashatg(
						Long.parseLong((String)principal.getName()))
		);
	}

	@GetMapping("{accountId}")
	public ResponseEntity selectAccountHashtag(
			final Pageable pageable,
			@PathVariable Long accountId
	) {
		return ResponseEntity.ok(
				accountHashtagService.selectAccountHashtagInDetail(accountId, pageable));
	}

	@GetMapping()
	public ResponseEntity selectAccountHashtag(
			@RequestParam(required = false) Long id,
			@RequestParam(required = true) Integer nHashtag,
			@RequestParam(required = false) Integer nAccount
	) {
		List<AccountHashtagVO> result = null;
		if(id != null) {
			result = accountHashtagService.selectAccountHashtag(id, nHashtag);
		}

		if(nAccount != null) {

			if(id != null)
				nAccount--;

			return ResponseEntity.ok(
				Stream.concat(
					result.stream(),
					accountHashtagService.selectAccountHashtagRandomly(Arrays.asList(id), nAccount, nHashtag).stream()
				).collect(Collectors.toList())
			);
		}

		return ResponseEntity.ok(result);
	}

	@PostMapping()
	public ResponseEntity createAccountHashtag(
			@RequestBody() List<AccountHashtagCreateDTO> data,
			Principal principal
	) {
		return ResponseEntity.ok(
				accountHashtagService.createAccountHashtag(
						Long.parseLong((String)principal.getName()), data)
		);
	}
}
