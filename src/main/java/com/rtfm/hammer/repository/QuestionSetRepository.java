package com.rtfm.hammer.repository;

import com.rtfm.hammer.model.QuestionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionSetRepository extends JpaRepository<QuestionSet, Integer> {

    /**
     * Find all question sets belonging to a specific team.
     *
     * @param teamId the team ID
     * @return list of question sets for the team
     */
    List<QuestionSet> findByTeamId(Integer teamId);
}
