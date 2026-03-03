package com.rtfm.hammer.repository;

import com.rtfm.hammer.model.McAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface McAnswerRepository extends JpaRepository<McAnswer, Integer> {

    /**
     * Find all answers for a specific question.
     *
     * @param questionId the question ID
     * @return list of multiple choice answers
     */
    List<McAnswer> findByQuestionId(Integer questionId);

    /**
     * Find all correct answers for a specific question.
     *
     * @param questionId the question ID
     * @return list of correct answers
     */
    List<McAnswer> findByQuestionIdAndIsCorrectTrue(Integer questionId);
}
