package com.archive.sukjulyo.news.controller;

import com.archive.sukjulyo.news.dto.NewsResponseDto;
import com.archive.sukjulyo.news.service.NaverNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Lazy
public class NaverNewsController  {
    private final NaverNewsService moviesService;

    @ResponseBody
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/api/v1/news/{keyword}")
    public NewsResponseDto get(@PathVariable String keyword) {
        return moviesService.findByKeyword(keyword);
    }
}
