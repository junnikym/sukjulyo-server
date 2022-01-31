package com.archive.sukjulyo.auth.dto;

import com.archive.sukjulyo.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class OAuthAttributesDTO {

	private String email;

	private String name;

//	private String picture;


	/**
	 * Convert Attribute to this DTO
	 *
	 * @param registrationId : which ragistration (google, kakao ... )
	 * @param userNameAttributeName : User name attribute key
	 * @param attributes : User data from the OAuth service.
	 * @return
	 */
	public static OAuthAttributesDTO of(
			String registrationId,
			String userNameAttributeName,
			Map<String, Object> attributes
	) {
		switch(registrationId) {

			case "kakao":
				return OAuthAttributesDTO.ofKakao(attributes);

			default:
				return null;
		}
	}

	/**
	 * Convert this DTO to Account Entity Object
	 *
	 * @return Account Entity Object
	 */
	public Account toAccount() {
		return Account.builder()
				.email(this.email)
				.name(this.name)
				.build();
	}

	/**
	 * It is extract attributes from each response that methods below
	 * and mapping to this DTO
	 */

	/**
	 * Mapping Kakao User Attributes to this DTO
	 *
	 * @param attributes : Kakao User Attributes
	 * @return
	 */
	private static OAuthAttributesDTO ofKakao(Map<String, Object> attributes) {

		Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) account.get("profile");

		return new OAuthAttributesDTO(
				(String) account.get("email"),
				(String) profile.get("nickname")
//				(String) profile.get("profile_image_url")
		);
	}


}
