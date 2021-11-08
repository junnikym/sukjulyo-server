package com.archive.sukjulyo.config;

import com.archive.sukjulyo.client.provider.OAuthProvider;
import com.archive.sukjulyo.client.service.OAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.ArrayList;
import java.util.List;

import static com.archive.sukjulyo.client.dto.LoginVO.KAKAO;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:oauth.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
				.antMatchers("/", "/oauth2/**", "/login/**", "/css/**", "/images/**", "/js/**", "/console/**", "/favicon.ico/**")
				.permitAll()
				.antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
				.anyRequest().authenticated()
			.and()
				.oauth2Login()
				.userInfoEndpoint().userService(new OAuthService())
			.and()
				.defaultSuccessUrl("/auth?is_success=true")
				.failureUrl("/auth?is_success=false")
			.and()
				.exceptionHandling();
//				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(
			@Value("${spring.security.oauth2.client.registration.kakao.client-id}") 		String kakaoClientId,
			@Value("${spring.security.oauth2.client.registration.kakao.client-secret}") 	String kakaoClientSecret
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

}