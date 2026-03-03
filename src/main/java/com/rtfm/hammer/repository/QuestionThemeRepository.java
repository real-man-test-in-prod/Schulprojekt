package com.rtfm.hammer.repository;

import com.rtfm.hammer.model.QuestionTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionThemeRepository extends JpaRepository<QuestionTheme, QuestionTheme.QuestionThemeId> {

    /**
     * Find all theme associations for a specific question.
     *
     * @param questionId the question ID
     * @return list of question theme associations
     */
    List<QuestionTheme> findByIdQuestionId(Integer questionId);

    /**
     * Find all question associations for a specific theme.
     *
     * @param themeId the theme ID
     * @return list of question theme associations
     */
    List<QuestionTheme> findByIdThemeId(Integer themeId);
}
