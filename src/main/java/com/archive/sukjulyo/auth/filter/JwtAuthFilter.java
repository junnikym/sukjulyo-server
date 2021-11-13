package com.archive.sukjulyo.auth.filter;

import com.archive.sukjulyo.util.CookieUtil;
import com.archive.sukjulyo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class JwtAuthFilter extends OncePerRequestFilter {

	private RequestMatcher requestMatcher = new AntPathRequestMatcher("/**");

	private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

	@Value("${jwt.token-name}")
	private String tokenHeader;

	@Resource
	private Environment env;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CookieUtil cookieUtil;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {

		if (requestMatcher.matches(request)) {

			String jwt = getAuthenticationToken(request);
			if (jwt.isEmpty())
				jwt = cookieUtil.getcookieValue(request, env.getProperty("jwt.token-name"));

			if (jwt.isEmpty()) {
				logger.error("not exist jwt at request");

				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			} else {

				Map<String, Object> attributes = jwtUtil.getBobyFromToken(jwt);

				if (!jwtUtil.isValidate(attributes))
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				if (jwtUtil.isExpired(attributes))
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);

				String userNameAttributeName = "sub";

				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

				OAuth2User userDetails = new DefaultOAuth2User(authorities, attributes, userNameAttributeName);
				OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(userDetails, authorities, userNameAttributeName);

				authentication.setDetails(userDetails);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Get a token form header at request
	 *
	 * @param request : HTTP request
	 * @return token string, if not exist returning null
	 */
	private String getAuthenticationToken(HttpServletRequest request)  {

		Enumeration<String> em = request.getHeaderNames();
		while(em.hasMoreElements()) {
			String s = em.nextElement();
			System.out.println(s + ": " + request.getHeader(s) + "<br/>");
		}

		String token = request.getHeader(this.tokenHeader);

		if( token != null ) {
			try {
				token = URLDecoder.decode(token, "UTF-8");
				String[] parts = token.split(" ");

				if( parts.length == 2 ) {
					String bearer = parts[0];
					String encodedToken = parts[1];
					return BEARER.matcher(bearer).matches() ? encodedToken : null;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
