package com.rtfm.hammer.repository;

import com.rtfm.hammer.model.GapField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GapFieldRepository extends JpaRepository<GapField, Integer> {

    /**
     * Find all gap fields for a specific question.
     *
     * @param questionId the question ID
     * @return list of gap fields ordered by gap index
     */
    List<GapField> findByQuestionIdOrderByGapIndex(Integer questionId);

    /**
     * Find gap fields for a question with their options loaded.
     *
     * @param questionId the question ID
     * @return list of gap fields with options
     */
    @Query("SELECT DISTINCT gf FROM GapField gf " +
            "LEFT JOIN FETCH gf.gapOptions " +
            "WHERE gf.questionId = :questionId " +
            "ORDER BY gf.gapIndex")
    List<GapField> findByQuestionIdWithOptions(@Param("questionId") Integer questionId);
}
