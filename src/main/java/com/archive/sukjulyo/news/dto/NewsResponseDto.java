package com.archive.sukjulyo.news.dto;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import java.util.Date;

@Data
public class NewsResponseDto {
//    private String title;
//    private String description;
//    private Date lastBuildDate;
//    private int total;
//    private int start;
    private int display;
    private Item[] items;
    @Data
    static class Item {
        public String title;
        public String link;
        public String image;
        public String subtitle;
        public Date pubDate;
        public String director;
        public String actor;
        public float userRating;
    }
}
