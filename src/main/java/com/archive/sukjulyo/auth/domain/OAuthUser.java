package com.archive.sukjulyo.auth.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OAuthUser {

	public static final String USER_ID 	= "clientId";
	public static final String USER_NAME 	= "clientName";
	public static final String USER_EMAIL 	= "clientEmail";

	public Map<String, Object> getOAuthUserAttributes(
			String registrationId,
			String response
	) throws JsonMappingException, JsonProcessingException {

		Map<String, Object> attributes = new HashMap<>();

		if ("kakao".equals(registrationId)){

			JsonNode node = new ObjectMapper().readTree(response);

			attributes.put(USER_ID, node.get("id").toString().replaceAll("\"", ""));
			attributes.put(USER_NAME, node.get("kakao_account").get("profile").get("nickname").toString());
			attributes.put(USER_EMAIL, node.get("kakao_account").get("email"));
		}

		return attributes;
	}
}
