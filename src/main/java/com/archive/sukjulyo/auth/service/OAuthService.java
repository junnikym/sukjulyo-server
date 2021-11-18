package com.archive.sukjulyo.auth.service;

import com.archive.sukjulyo.auth.domain.OAuthUser;
import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.client.dto.ClientCreateDTO;
import com.archive.sukjulyo.client.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OAuthService extends DefaultOAuth2UserService {

	private static final String MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri";
	private static final String MISSING_USER_ACCESS_TOEKN_ERROR_CODE = "missing_user_access_toeken";
	private static final String MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE = "missing_user_name_attribute";
	private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";

	@Autowired
	private OAuthUser oAuthUser;

	@Autowired
	private ClientService clientService;

	@SneakyThrows
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		Assert.notNull(userRequest, "userRequest cannot be null");

		String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();
		String resourceServerUri = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
		String accessToken = userRequest.getAccessToken().getTokenValue();

		if (!StringUtils.hasText(resourceServerUri)) {
			OAuth2Error oauth2Error = new OAuth2Error(MISSING_USER_INFO_URI_ERROR_CODE, "Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: " + userRequest.getClientRegistration().getRegistrationId(), null);
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
		}
		if (!StringUtils.hasText(accessToken)) {
			OAuth2Error oauth2Error = new OAuth2Error(MISSING_USER_ACCESS_TOEKN_ERROR_CODE, "Missing required access toekn in UserInfoEndpoint for Client Registration: " + userRequest.getClientRegistration().getRegistrationId(), null);
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
		}

		OAuth2User user = null;

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		String userNameAttributeName = OAuthUser.USER_ID;

		Map<String, Object> attributes = null;

		// < Header >
		// Put access-token in the response header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + accessToken);

		// < Body >
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

		try {
			String response = restTemplate.postForObject(resourceServerUri, request, String.class);
			attributes = oAuthUser.getOAuthUserAttributes(clientRegistrationId, response);

			/**
			 * Save the client
			 */
			Long clientId = Long.parseLong((String) attributes.get("clientId"));
			var client = clientService.selectClient(clientId);

			if(client != null && client.getRole() == Client.Role.ROLE_ADMIN)
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

			var dto = ClientCreateDTO.builder()
					.id(clientId)
					.build();

			var ent = clientService.saveAndUpdateClient(client, dto);
		} catch (OAuth2AuthorizationException ex) {
			OAuth2Error oauth2Error = ex.getError();
			throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return new DefaultOAuth2User(authorities, attributes, userNameAttributeName);
	}

}