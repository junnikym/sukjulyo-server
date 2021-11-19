package com.archive.sukjulyo.news.repository;

import com.archive.sukjulyo.news.domain.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

	Optional<News> findById(Long id);

	List<News> findAllByPubDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

	Boolean existsByLink(String link);

}
