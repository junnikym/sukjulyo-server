package com.archive.sukjulyo.auth.service;

import com.archive.sukjulyo.account.domain.Account;
import com.archive.sukjulyo.account.service.AccountService;
import com.archive.sukjulyo.auth.dto.OAuthAttributesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {

	private final AccountService accountService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

		Assert.notNull(request, "(OAuthService - loadUser) request cannot be null");

		OAuth2User oAuth2User = super.loadUser(request);

		String registrationId = request
				.getClientRegistration()
				.getRegistrationId();

		String userNameAttributeName = request
				.getClientRegistration()
				.getProviderDetails()
				.getUserInfoEndpoint()
				.getUserNameAttributeName();

		OAuthAttributesDTO attributes = OAuthAttributesDTO.of(
				registrationId,
				userNameAttributeName,
				oAuth2User.getAttributes()
		);

		accountService.insertAndUpdateAccount(attributes);

		return new DefaultOAuth2User(
				Collections.singleton( new SimpleGrantedAuthority("ADMIN") ),
				oAuth2User.getAttributes(),
				userNameAttributeName
		);
	}

}