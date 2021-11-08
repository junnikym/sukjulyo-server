package com.archive.sukjulyo.news.service;

import com.archive.sukjulyo.news.api.NewsApiClient;
import com.archive.sukjulyo.news.dto.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NaverNewsService {
    private final NewsApiClient movieApiClient;
    @Transactional(readOnly = true)
    public NewsResponseDto findByKeyword(String keyword) {
        return movieApiClient.requestNews(keyword);
    }
}
