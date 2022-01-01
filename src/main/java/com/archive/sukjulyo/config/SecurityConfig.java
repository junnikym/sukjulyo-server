package com.archive.sukjulyo.config;

import com.archive.sukjulyo.auth.filter.JwtAuthFilter;
import com.archive.sukjulyo.auth.handler.LogoutSuccessHandler;
import com.archive.sukjulyo.auth.handler.OAuthSuccessHandler;
import com.archive.sukjulyo.auth.provider.OAuthProvider;
import com.archive.sukjulyo.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

import static com.archive.sukjulyo.client.dto.LoginVO.KAKAO;

@Configuration
@EnableWebSecurity
@RefreshScope
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final OAuthSuccessHandler oAuthSuccessHandler;
	private final LogoutSuccessHandler logoutSuccessHandler;

	@Value("${frontend-app.entry}")
	private String frontUrl;

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity

				.httpBasic().disable()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
				.authorizeRequests()
					.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
					.antMatchers(
							"/", "/oauth2/**", "/login/**",
							"/css/**", "/images/**", "/js/**", "/console/**", "/favicon.ico/**",
							"/hashtag/**", "/news/**").permitAll()
					.antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
					.anyRequest().authenticated()

			.and()
				.oauth2Login()
					.redirectionEndpoint()
						.baseUri("/login/oauth2/code/*") // 디폴트는 login/oauth2/code/*
					.and()
						.userInfoEndpoint().userService(OAuth2UserService())
					.and()
						.successHandler(oAuthSuccessHandler)
//					.failureUrl("/main.do")

			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
				.logout()
					.deleteCookies("JSESSIONID")
					.logoutSuccessHandler(logoutSuccessHandler)

			.and()
				.addFilterBefore(jwtAuthFilter(), OAuth2AuthorizationRequestRedirectFilter.class)
				.cors();
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(
			@Value("${spring.security.oauth2.client.registration.kakao.client-id}") 		String kakaoClientId,
			@Value("${spring.security.oauth2.client.registration.kakao.client-secret}") 		String kakaoClientSecret
	) {
		List<ClientRegistration> registrations = new ArrayList<>();

		registrations.add(
				OAuthProvider.KAKAO.getBuilder("kakao")
						.clientId(kakaoClientId)
						.clientSecret(kakaoClientSecret)
						.jwkSetUri("temp")
						.build());

		return new InMemoryClientRegistrationRepository(registrations);
	}

	@Bean
	public OAuth2UserService<OAuth2UserRequest, OAuth2User> OAuth2UserService() {
		return new OAuthService();
	}

	@Bean
	public JwtAuthFilter jwtAuthFilter() {
		return new JwtAuthFilter();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin(frontUrl);
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}