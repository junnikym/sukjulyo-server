package com.archive.sukjulyo.hashtag.repository;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.dto.HashtagFreqResponseVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findById(Long id);

    Optional<Hashtag> findByTag(String tag);

    @Query(value = "SELECT h.tag AS tag, COUNT(*) AS freq"
                    + "    FROM news_hashtag AS nh"
                    + "    LEFT JOIN hashtag AS h ON nh.hashatag_fk = h.id"
                    + "    LEFT JOIN news AS n ON nh.news_fk = n.id"
                    + "    WHERE DATE(n.pub_date)"
                    + "       BETWEEN :start_t"
                    + "       AND :end_t"
                    + "    GROUP BY nh.hashatag_fk"
                    + "    ORDER BY freq DESC"
                    + "    LIMIT :limit_n",
            nativeQuery = true)
    List<HashtagFreqResponseVO> findHashtagFreqByDate(
            @Param("limit_n")   int n,
            @Param("start_t")   LocalDateTime startTime,
            @Param("end_t")     LocalDateTime endTime
    );
}