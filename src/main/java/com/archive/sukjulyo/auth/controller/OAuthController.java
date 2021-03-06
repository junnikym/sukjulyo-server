package com.archive.sukjulyo.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class OAuthController {

	@Value("${frontend-app.entry}")
	private String frontUrl;

	@GetMapping()
	public void loginCallback(
			@RequestParam(required = false) String token,
			HttpServletResponse httpServletResponse
	) throws IOException {
		httpServletResponse.sendRedirect(frontUrl);
	}

}
