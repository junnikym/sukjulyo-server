package com.archive.sukjulyo.config.security;

import com.archive.sukjulyo.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final OAuthService oAuthService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.headers().frameOptions().disable()
				.and()
					.authorizeRequests()
						.anyRequest().permitAll()
				.and()
					.logout().logoutSuccessUrl("/")
				.and()
					.oauth2Login()
						.redirectionEndpoint().baseUri("/oauth2/callback/**")
						.and()
						.userInfoEndpoint().userService(oAuthService);
	}
}