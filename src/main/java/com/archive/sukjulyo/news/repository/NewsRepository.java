package com.archive.sukjulyo.news.repository;

import com.archive.sukjulyo.news.domain.News;
import com.archive.sukjulyo.news.dto.NewsUpdateDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

	Optional<News> findById(Long id);

	List<News> findAllByPubDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE news n " +
				   "SET n.summary = (:#{#dto.summary}) " +
				   "WHERE  n.link = (:#{#dto.link})",
			nativeQuery = true)
	int updateSummary(@Param("dto") NewsUpdateDTO dto);

	Boolean existsByLink(String link);

}
