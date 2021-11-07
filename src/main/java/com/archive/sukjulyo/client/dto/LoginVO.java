package com.archive.sukjulyo.client.dto;

public enum LoginVO {
	KAKAO("kakao");

	private final String ROLE_PREFIX = "ROLE_";
	private String name;

	LoginVO(String name) { this.name = name; }

	public String getRoleType() { return ROLE_PREFIX + name.toUpperCase(); }

	public String getValue() { return name; }

	public boolean isEquals(String authority) { return this.getRoleType().equals(authority);}
}