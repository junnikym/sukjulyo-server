package com.archive.sukjulyo.news.api;

import com.archive.sukjulyo.news.dto.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Lazy
public class NewsApiClient {
    private final RestTemplate restTemplate;
    private final String CLIENT_ID = "UYxdo4V4sMjuW2QnWyiT";
    private final String CLIENT_SECRET = "8NTPHjhL0N";
    private final String OpenNaverNewsUrl_getNews = "https://openapi.naver.com/v1/search/news.json?query={keyword}";

    public NewsResponseDto requestNews(String keyword) {
        final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(OpenNaverNewsUrl_getNews, HttpMethod.GET, entity, NewsResponseDto.class, keyword).getBody();
    }
}
