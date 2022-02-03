package com.archive.sukjulyo.hashtag.repository;

import com.archive.sukjulyo.hashtag.domain.Hashtag;
import com.archive.sukjulyo.hashtag.vo.HashtagFreqResponseVO;
import com.archive.sukjulyo.hashtag.vo.HashtagFreqTopNResponseVO;
import com.archive.sukjulyo.util.enums.Period;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findById(Long id);

    Optional<List<Hashtag>> findAllByIdIn(List<Long> ids);

    Optional<Hashtag> findByTag(String tag);

    @Query(value = "SELECT h.tag AS tag, COUNT(*) AS freq" +
                   "     FROM news_hashtag AS nh" +
                   "     LEFT JOIN hashtag AS h ON nh.hashatag_fk = h.id" +
                   "     LEFT JOIN news AS n ON nh.news_fk = n.id" +
                   "     WHERE (" +
                   "         DATE(n.pub_date)" +
                   "             BETWEEN :start_t" +
                   "             AND :end_t" +
                   "     ) AND h.is_noise = false" +
                   "     GROUP BY nh.hashatag_fk" +
                   "     ORDER BY freq DESC" +
                   "     LIMIT :limit_n" +
                   "     OFFSET :offset_n",
            nativeQuery = true)
    List<HashtagFreqResponseVO> findHashtagFreqByDate(
            @Param("limit_n")   int limit_n,
            @Param("offset_n")  int offset_n,
            @Param("start_t")   LocalDateTime startTime,
            @Param("end_t")     LocalDateTime endTime
    );

    @Query(value = "SELECT tag, hashtag_freq.freq as freq, " +
                   "    pub_date as date, row_num as `rank`" +
                   "FROM (" +
                   "    SELECT" +
                   "        h.tag," +
                   "        COUNT(*) AS freq," +
                   "        EXTRACT(DAY from pub_date) AS pub_date," +
                   "        (" +
                   "            ROW_NUMBER() OVER(" +
                   "                PARTITION BY EXTRACT(DAY from pub_date)" +
                   "                ORDER BY COUNT(*) DESC" +
                   "            )" +
                   "        ) row_num" +
                   "    FROM news_hashtag AS nh" +
                   "    LEFT JOIN hashtag AS h ON nh.hashatag_fk=h.id" +
                   "    LEFT JOIN news AS n ON nh.news_fk=n.id" +
                   "    WHERE (" +
                   "         DATE(n.pub_date)" +
                   "             BETWEEN :start_t" +
                   "             AND :end_t" +
                   "     ) AND h.is_noise = false" +
                   "    GROUP BY " +
                   "        (" +
                   "            CASE :period " +
                   "            WHEN 'DAY' THEN EXTRACT(DAY FROM DATE(n.pub_date))" +
                   "            END" +
                   "        ), " +
                   "        nh.hashatag_fk" +
                   "    ORDER BY freq DESC" +
                   ") hashtag_freq " +
                   "WHERE :offset_n <= row_num AND row_num <= :limit_n ",
            nativeQuery = true)
    List<HashtagFreqTopNResponseVO> findHashtagFreqNth(
            @Param("limit_n")   int             limit_n,
            @Param("offset_n")  int             offset_n,
            @Param("start_t")   LocalDateTime   startTime,
            @Param("end_t")     LocalDateTime   endTime,
            @Param("period")    Period          period
    );

    List<Hashtag> findByIsNoiseIsNotNull(Pageable pageable);

    List<Hashtag> findByTagContaining(String tag, Pageable pageable);
}