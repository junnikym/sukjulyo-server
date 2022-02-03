package com.archive.sukjulyo.hashtag.vo;

import com.archive.sukjulyo.hashtag.domain.Hashtag;

import java.util.UUID;

public interface HashtagPreferenceVO {

	UUID getId();

	Integer getScore();

	Hashtag getHashtag();

}
