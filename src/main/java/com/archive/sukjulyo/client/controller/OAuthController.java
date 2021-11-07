package com.archive.sukjulyo.client.controller;

import com.archive.sukjulyo.client.dto.OAuthTokenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class OAuthController {

	@GetMapping()
	public ResponseEntity isLoginCallback(@RequestParam("is_success") Boolean isSuccess) {
		if(isSuccess)
			return ResponseEntity.ok(true);
		else
			return ResponseEntity.ok(false);
	}

}
