package com.archive.sukjulyo.auth.handler;

import com.archive.sukjulyo.util.CookieUtil;
import lombok.RequiredArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	private final Environment env;

	private final CookieUtil cookieUtil;

	@Override
	public void onLogoutSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
	) throws IOException, ServletException {

		String frontendAppEntryPage = env.getProperty("frontend-app.entry");

		response.addCookie(cookieUtil.generateRemoveJwtCookie(env.getProperty("jwt.token-name"), ""));
		response.addCookie(cookieUtil.generateRemoveJwtCookie(env.getProperty("jwt.token-name") + "-flag", ""));

		getRedirectStrategy().sendRedirect(request, response, frontendAppEntryPage);

	}

}
