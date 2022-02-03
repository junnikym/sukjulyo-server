package com.archive.sukjulyo.hashtag.controller;

import com.archive.sukjulyo.hashtag.dto.AccountHashtagCreateDTO;
import com.archive.sukjulyo.hashtag.service.HashtagPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/hashtag/account")
@RequiredArgsConstructor
public class AccountHashtagController {

	private final HashtagPreferenceService hashtagPreferenceService;

	@GetMapping("/has")
	public ResponseEntity hasHashtag(Principal principal) {
		return ResponseEntity.ok(
				hashtagPreferenceService.hasAccountHashtag(
						Long.parseLong((String)principal.getName()))
		);
	}

	@GetMapping("{accountId}")
	public ResponseEntity selectAccountHashtag(
			final Pageable pageable,
			@PathVariable UUID accountId
	) {
		return ResponseEntity.ok(
				hashtagPreferenceService.selectAccountHashtagInDetail(accountId, pageable));
	}

//	@GetMapping()
//	public ResponseEntity selectAccountHashtag(
//			@RequestParam(required = false) UUID id,
//			@RequestParam(required = true) Integer nHashtag,
//			@RequestParam(required = false) Integer nAccount
//	) {
//		List<HashtagPreferenceVO> result = null;
//		if(id != null) {
//			result = hashtagPreferenceService.selectAccountHashtag(id, nHashtag);
//		}
//
//		if(nAccount != null) {
//
//			if(id != null)
//				nAccount--;
//
//			return ResponseEntity.ok(
//				Stream.concat(
//					result.stream(),
//					hashtagPreferenceService.selectAccountHashtagRandomly(Arrays.asList(id), nAccount, nHashtag).stream()
//				).collect(Collectors.toList())
//			);
//		}
//
//		return ResponseEntity.ok(result);
//	}

	@PostMapping()
	public ResponseEntity createAccountHashtag(
			@RequestBody() List<AccountHashtagCreateDTO> data,
			Principal principal
	) {
		return ResponseEntity.ok(
				hashtagPreferenceService.createAccountHashtag(
						Long.parseLong((String)principal.getName()), data)
		);
	}
}
