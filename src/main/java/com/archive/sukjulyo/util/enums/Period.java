package com.archive.sukjulyo.util.enums;

import java.time.Duration;

public enum Period {
	DAY;

	public Duration toDuration() {
		switch(this) {
			case DAY:
				return Duration.ofDays(1);
			default:
				return null;
		}
	}
}
