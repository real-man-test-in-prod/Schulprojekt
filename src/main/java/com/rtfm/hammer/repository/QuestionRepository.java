package com.rtfm.hammer.repository;

import com.rtfm.hammer.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    /**
     * Find Question by ID;
     * @param questionId the question ID
     * @return question
     */

    Question findByQuestionId(Integer questionId);

    /**
     * Find all questions in a specific question set.
     *
     * @param questionSetId the question set ID
     * @return list of questions in the set
     */
    List<Question> findByQuestionSetId(Integer questionSetId);

    /**
     * Find all questions of a specific type (MC, TF, GAP).
     *
     * @param questionType the question type
     * @return list of questions of the specified type
     */
    List<Question> findByQuestionType(String questionType);

    /**
     * Find all questions associated with a specific theme.
     *
     * @param themeId the theme ID
     * @return list of questions with the theme
     */
    @Query("SELECT DISTINCT q FROM Question q " +
            "JOIN q.questionThemes qt " +
            "WHERE qt.id.themeId = :themeId")
    List<Question> findByThemeId(@Param("themeId") Integer themeId);

    /**
     * Find all questions in a specific question set with their nested collections
     * loaded.
     *
     * @param questionSetId the question set ID
     * @return list of questions with answers and gap fields
     */
    @Query("SELECT DISTINCT q FROM Question q " +
            "LEFT JOIN FETCH q.mcAnswers " +
            "LEFT JOIN FETCH q.gapFields " +
            "LEFT JOIN FETCH q.questionThemes " +
            "WHERE q.questionSetId = :questionSetId")
    List<Question> findByQuestionSetIdWithDetails(@Param("questionSetId") Integer questionSetId);
}
