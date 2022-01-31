package com.archive.sukjulyo.account.enumerate;

public enum AccountRole {
	ADMIN(0),
	NORMAL(1);

	private final Integer code;

	public static AccountRole defaultRole() {
		return AccountRole.NORMAL;
	}

	AccountRole (Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}
}