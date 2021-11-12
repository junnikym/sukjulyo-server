package com.archive.sukjulyo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final Environment env;

	/**
	 * Validate check for the value came through the parameter by the body
	 *
	 * @param body : body from token
	 * @return is validate or not
	 */
	public boolean isValidate(Map<String,Object> body) {
		try {
			final String subject = (String) (body.get("sub"));
			return !subject.isEmpty();
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Validate check for the value came through the parameter by the token
	 *
	 * @param token : JWT value
	 * @return is validate or not
	 */
	public boolean isValidateToken(String token) {
		return isValidate(getBobyFromToken(token));
	}

	/**
	 * Check to if the token has expired by the body
	 *
	 * @param body : body from token
	 * @return is expired or not
	 */
	public boolean isExpired(Map<String,Object> body) {
		try {
			long exp = (Long) (body.get("exp"));
			final Date expiration = new Date(exp);
			return expiration.before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Check to if the token has expired by the token
	 *
	 * @param token : JWT value
	 * @return is expired or not
	 */
	public boolean isTokenExpired(String token) {
		return this.isExpired(getBobyFromToken(token));
	}

	/**
	 * Generate token
	 *
	 * @param client : clinet contents
	 * @param <T> : cleint object type
	 * @return token string
	 */
	public <T> String generateToken(T client) {

		Map<String,Object> claim = new HashMap<>();

		if (client instanceof DefaultOAuth2User) {

			claim.put("iss", env.getProperty("jwt.toekn-issuer"));
			claim.put("sub",  ((DefaultOAuth2User) client).getName());

//			claim.put("email", ((DefaultOAuth2User) client).getAttributes().get("userEmail"));
			claim.put("nickname", ((DefaultOAuth2User) client).getAttributes().get("userName"));
		}

		String secret = env.getProperty("jwt.secret");
		int exp = Integer.valueOf(env.getProperty("jwt.expire-time"));

		claim.put("iat", new Date(System.currentTimeMillis()));
		claim.put("exp", new Date(System.currentTimeMillis() + (1000 * exp)));

		return Jwts.builder()
				.setClaims(claim)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	/**
	 * Get Body content from token
	 *
	 * @param token : token string
	 * @return body contents
	 */
	public Map<String,Object> getBobyFromToken(String token){
		String secret = env.getProperty("jwt.secret");
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

}