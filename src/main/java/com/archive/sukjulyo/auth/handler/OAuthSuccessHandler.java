package com.archive.sukjulyo.auth.handler;

import com.archive.sukjulyo.util.CookieUtil;
import com.archive.sukjulyo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final Environment env;

	private final JwtUtil jwtUtil;

	private final CookieUtil cookieUtil;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
	) throws IOException, ServletException {

		String jwt = jwtUtil.generateToken((DefaultOAuth2User) authentication.getPrincipal());
		String redirectUri = "/auth?token="+jwt;

		//authentication.getAuthorities()

		response.addCookie(cookieUtil.generateNormalCookie(
				env.getProperty("jwt.token-name"),
				jwt,
				Integer.valueOf(env.getProperty("jwt.expire-time")).intValue())
		);
		response.addCookie(cookieUtil.generateNormalCookie(
				env.getProperty("jwt.token-name") + "-flag",
				"true",
				Integer.valueOf(env.getProperty("jwt.expire-time")).intValue())
		);

		if (response.isCommitted()) return;

		getRedirectStrategy().sendRedirect(request, response, redirectUri);
	}

}
